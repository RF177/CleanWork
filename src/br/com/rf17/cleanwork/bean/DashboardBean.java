package br.com.rf17.cleanwork.bean;

import java.text.SimpleDateFormat;
import java.util.List;

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
import br.com.rf17.cleanwork.model.dashboard.Dashboard_producao;

@ManagedBean
@ViewScoped
public class DashboardBean {

	DashboardDao dashboardDao = new DashboardDao();
	
	private LineChartModel compras_vendas;
	private BarChartModel producao;
	private PieChartModel financeiro;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
		
		financeiro.setShowDataLabels(true);
		financeiro.set("Contas a Pagar", compras);
		financeiro.set("Contas a Receber", vendas);
		financeiro.setTitle("Financeiro");
		financeiro.setShowDataLabels(true);
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
		compras_vendas.getAxes().put(AxisType.X, dateAxis);
        
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Compras");
		for (int i = 1; i < 13; i++) {
			series1.set("2015-0"+i+"-01", 0.0);//FIXME
		}
		for(Dashboard_financeiro f : dashboardDao.buscaValorComprasMes()){			
			series1.set(sdf.format(f.getData()), f.getValor());			
		}
		compras_vendas.addSeries(series1);
		
		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Vendas");
		for (int i = 1; i < 13; i++) {
			series2.set("2015-0"+i+"-01", 0.0);//FIXME
		}
		for(Dashboard_financeiro f : dashboardDao.buscaValorVendasMes()){			
			series2.set(sdf.format(f.getData()), f.getValor());			
		}
		compras_vendas.addSeries(series2);

	}

	private void iniciaProducao() throws Exception {
		producao = new BarChartModel();
		producao.setTitle("Produção");
		producao.setAnimate(true);
		producao.setSeriesColors("629b58");
		
		List<Dashboard_producao> producaos = dashboardDao.buscaProducao();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		ChartSeries series1 = new ChartSeries();
		for(Dashboard_producao f : producaos){			
			series1.set(sdf.format(f.getData()), f.getQtd());			
		}
		
		producao.addSeries(series1);

	}

}
