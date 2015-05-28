package br.com.rf17.cleanwork.bean;

import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

import br.com.rf17.cleanwork.dao.DashboardDao;
import br.com.rf17.cleanwork.model.dashboard.Dashboard_financeiro;

@ManagedBean
@ViewScoped
public class DashboardBean {

	DashboardDao dashboardDao = new DashboardDao();
	
	private LineChartModel compras_vendas;
	private BarChartModel producao;
	private PieChartModel financeiro;

	public DashboardBean() {
		criaGraficos();
	}

	public LineChartModel getCompras_vendas() {
		return compras_vendas;
	}

	public void setCompras_vendas(LineChartModel compras_vendas) {
		this.compras_vendas = compras_vendas;
	}

	public BarChartModel getProducao() {
		return producao;
	}

	public void setProducao(BarChartModel producao) {
		this.producao = producao;
	}

	public PieChartModel getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(PieChartModel financeiro) {
		this.financeiro = financeiro;
	}

	private void criaGraficos() {
		try{
			iniciaComprasVendas();
			iniciaProducao();
			iniciaFinanceiro();			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", e.getMessage()));
			e.printStackTrace();
		}
	}
	
	private void iniciaFinanceiro() throws Exception{
		financeiro = new PieChartModel();
		
		double compras = dashboardDao.buscaValorCompras().get(0).getValor();
		double vendas = dashboardDao.buscaValorVendas().get(0).getValor();
		
		double porcent_compras = (compras / (compras+vendas)) * 100;
		double porcent_vendas = (vendas / (vendas+compras)) * 100;
		
		financeiro.setShowDataLabels(true);
		financeiro.set("Contas a Pagar", porcent_compras);
		financeiro.set("Contas a Receber", porcent_vendas);
		financeiro.setTitle("Financeiro");
		financeiro.setLegendPosition("ne");
		financeiro.setSeriesColors("d15b47,629b58");
	}

	private void iniciaComprasVendas() throws Exception {
		compras_vendas = new LineChartModel();
		compras_vendas.setTitle("Compras e Vendas");
		compras_vendas.setAnimate(true);
		compras_vendas.setLegendPosition("ne");
		compras_vendas.setSeriesColors("d15b47,629b58");
		DateAxis dateAxis = new DateAxis();
		dateAxis.setTickAngle(-7);
		dateAxis.setTickFormat("%m / %Y");//https://github.com/mbostock/d3/wiki/Time-Formatting
		dateAxis.setTickInterval("2628000000");//1 mes em miliseconds
		
        //Calendar cal = Calendar.getInstance();
	    //cal.set(Calendar.DAY_OF_YEAR, 1);
	    //dateAxis.setMin("1420077600000");//FIXME
        
        //cal.set(Calendar.DAY_OF_YEAR, 365); // for leap years
        //dateAxis.setMax(cal.getTime());//FIXME
		
		//dateAxis.setMax("2015-02-01");
	    //dateAxis.setMax("1451527200000");
        
        compras_vendas.getAxes().put(AxisType.X, dateAxis);
        
        
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Compras");
		for (int i = 1; i < 13; i++) {
			series1.set("2015-0"+i+"-01", 0.0);//FIXME
		}
		for(Dashboard_financeiro f : dashboardDao.buscaValorComprasMes()){			
			series1.set(new SimpleDateFormat("yyyy-MM-dd").format(f.getData()), f.getValor());			
		}
		compras_vendas.addSeries(series1);
		
		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Vendas");
		series2.set("2015-01-01", 0.0);
		for (int i = 1; i < 13; i++) {
			series2.set("2015-0"+i+"-01", 0.0);//FIXME
		}
		for(Dashboard_financeiro f : dashboardDao.buscaValorVendasMes()){			
			series2.set(new SimpleDateFormat("yyyy-MM-dd").format(f.getData()), f.getValor());			
		}
		series2.set("2015-12-01", 0.0);
		compras_vendas.addSeries(series2);
			
		

	}

	private void iniciaProducao() {
		producao = new BarChartModel();
		producao.setTitle("Produção");
		producao.setAnimate(true);
		producao.setLegendPosition("ne");
		//Axis yAxis_producao = producao.getAxis(AxisType.Y);
		//yAxis_producao = producao.getAxis(AxisType.Y);
		//yAxis_producao.setMin(0);
		//yAxis_producao.setMax(200);
		producao.setSeriesColors("E0BB26,629B58,3399CC,D15B47");
		
		
		ChartSeries pendente = new ChartSeries();
		pendente.setLabel("Pendentes");
		//pendente.set("01/2015", 10);
		//pendente.set("02/2015", 10);
		//pendente.set("03/2015", 40);
		pendente.set("04/2015", 10);
		
		ChartSeries em_producao = new ChartSeries();
		em_producao.setLabel("Em Produção");
		//em_producao.set("01/2015", 102);
		//em_producao.set("02/2015", 100);
		//em_producao.set("03/2015", 40);
		em_producao.set("04/2015", 105);

		ChartSeries concluido = new ChartSeries();
		concluido.setLabel("Concluídas");
		//concluido.set("01/2015", 110);
		//concluido.set("02/2015", 160);
		//concluido.set("03/2015", 140);
		concluido.set("04/2015", 150);

		ChartSeries cancelada = new ChartSeries();
		cancelada.setLabel("Canceladas");
		//cancelada.set("01/2015", 14);
		//cancelada.set("02/2015", 17);
		//cancelada.set("03/2015", 54);
		cancelada.set("04/2015", 97);

		producao.addSeries(pendente);
		producao.addSeries(em_producao);
		producao.addSeries(concluido);
		producao.addSeries(cancelada);

	}

}
