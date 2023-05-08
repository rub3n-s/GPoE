package pt.isec.pa.apoio_poe.ui.gui.consulta;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.ModelManager;
import pt.isec.pa.apoio_poe.model.data.Docente;
import pt.isec.pa.apoio_poe.model.data.Singleton;
import pt.isec.pa.apoio_poe.model.fsm.ManagementState;
import java.util.HashMap;

public class ConsultaUI extends BorderPane {
    ModelManager model;
    Singleton singleton;

    VBox menuInicial;
    Button [] menuInicialBtns;

    public ConsultaUI(ModelManager model) {
        this.model = model;
        singleton = Singleton.Singleton();

        menuInicial = new VBox(10);
        menuInicialBtns = new Button[7];

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #ff80c4;");

        Label label = new Label("CONSULTA");
        label.setStyle("-fx-font: 24 arial;");
        label.setPadding(new Insets(50,50,-50,50));
        HBox title = new HBox(label);
        title.setAlignment(Pos.CENTER);
        this.setTop(title);

        /* Menu Inicial */
        this.setCenter(menuInicial);
        for (int i = 0; i < menuInicialBtns.length; i++)  {
            this.getChildren().add(menuInicialBtns[i] = new Button());
            switch(i) {
                case 0 -> menuInicialBtns[i].setText("Mostrar Graficos");
                case 1 -> menuInicialBtns[i].setText("Alunos Com Propostas Atribuidas");
                case 2 -> menuInicialBtns[i].setText("Sem Propostas mas Com Candidatura");
                case 3 -> menuInicialBtns[i].setText("Propostas Disponiveis");
                case 4 -> menuInicialBtns[i].setText("Propostas Atribuidas");
                case 5 -> menuInicialBtns[i].setText("Orientacoes");
                case 6 -> menuInicialBtns[i].setText("Exportar Fase 5");
            }
            menuInicialBtns[i].setMinWidth(235);
            menuInicial.getChildren().add(menuInicialBtns[i]);
        }
        menuInicial.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        menuInicialBtns[0].setOnAction(actionEvent -> mostrarPieChart());           // Mostrar PieChart
        menuInicialBtns[1].setOnAction(actionEvent -> alunosPropAtribuidas());     // Alunos com Propostas Atribuidas
        menuInicialBtns[2].setOnAction(actionEvent -> alunosSemPropComCand());     // Alunos sem Proposta mas com Candidatura
        menuInicialBtns[3].setOnAction(actionEvent -> propostasDisponiveis());     // Propostas Disponiveis
        menuInicialBtns[4].setOnAction(actionEvent -> propostasAtribuidas());      // Consultar Atribuicao Docentes
        menuInicialBtns[5].setOnAction(actionEvent -> orientacoes());              // Orientacoes
        menuInicialBtns[6].setOnAction(actionEvent -> exportarFase5());            // Exportar Fase 5
    }

    private void update() { this.setVisible(model != null && model.getState() == ManagementState.CONSULTA); }

    private void alunosPropAtribuidas() {
        model.consultarAlunosAtribuidas();
        showAlert("Alunos Com Propostas Atribuidas");
    }

    private void alunosSemPropComCand() {
        model.consultarSemPropComCandidatura();
        showAlert("Sem Propostas mas Com Candidatura");
    }

    private void propostasDisponiveis() {
        model.consultarPropostasNaoAtribuidas();
        showAlert("Propostas Disponiveis");
    }

    private void propostasAtribuidas() {
        model.consultarPropostasAtribuidas();
        showAlert("Propostas Atribuidas");
    }

    private void orientacoes() {
        model.consultarOrientacoes();
        showAlert("Orientacoes");
    }

    private  void exportarFase5() {
        model.exportarFase4e5();
        showAlert("Exportar Fase 5");
    }

    private void mostrarPieChart() {
        /*================================= PieChart Ramos =================================*/
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("DA", model.getEstagiosDA()),
                new PieChart.Data("SI", model.getEstagiosSI()),
                new PieChart.Data("RAS", model.getEstagiosRAS()),
                new PieChart.Data("Propostas Atribuidas", model.getNumAtribuidas()),
                new PieChart.Data("Propostas Nao Atribuidas", model.getNumNAtribuidas())
        );
        // criar um PieChart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setLegendSide(Side.LEFT);
        pieChart.setPrefSize(500,300);
        pieChart.setTitle("Estagios por Ramo e Propostas (Hover para % e V.A.)");
        pieChart.getAnimated();
        pieChart.setLabelsVisible(true);
        pieChart.getData().forEach(data -> {
            double total = 0;
            for (PieChart.Data d : pieChart.getData()) {
                total += d.getPieValue();
            }
            String percentage = String.format("%.1f%%", 100*data.getPieValue()/total) ;
            Tooltip tooltip = new Tooltip(percentage + ", V.A.: " + data.getPieValue());
            Tooltip.install(data.getNode(),tooltip);
        });

        /*================================= BarChart Docentes =================================*/
        // criar os eixos para o BarChart dos Docentes
        CategoryAxis xAxisDocentes = new CategoryAxis();
        xAxisDocentes.setLabel("Docentes");

        NumberAxis yAxisDocentes = new NumberAxis();
        yAxisDocentes.setLabel("Orientacoes");

        // criar o barChart dos Docentes
        BarChart<String,Number> barChartDocentes = new BarChart<>(xAxisDocentes, yAxisDocentes);

        XYChart.Series<String,Number> dataSeriesDocentes = new XYChart.Series<>();
        dataSeriesDocentes.setName("Top 5: Orientacoes Docentes");

        HashMap<Docente,Integer> top5Docentes = model.getDocentesTop5();

        for (var d : top5Docentes.entrySet())
            dataSeriesDocentes.getData().add(new XYChart.Data<>(d.getKey().getNome(), d.getValue()));
        barChartDocentes.getData().add(dataSeriesDocentes);
        barChartDocentes.setPrefSize(400,300);

        /*================================= BarChart Empresas =================================*/
        // criar os eixos para o BarChart das Empresas
        CategoryAxis xAxisEmpresas = new CategoryAxis();
        xAxisEmpresas.setLabel("Entidades");

        NumberAxis yAxisEmpresas = new NumberAxis();
        yAxisEmpresas.setLabel("Propostas");

        // criar o barChart dos Docentes
        BarChart<String,Number> barChartEmpresas = new BarChart<>(xAxisEmpresas, yAxisEmpresas);

        XYChart.Series<String,Number> dataSeriesEmpresas = new XYChart.Series<>();
        dataSeriesEmpresas.setName("Top 5: Entidades");

        HashMap<String,Integer> top5Empresas = model.getEmpresasTop5();

        for (var d : top5Empresas.entrySet())
            dataSeriesEmpresas.getData().add(new XYChart.Data<>(d.getKey(), d.getValue()));
        barChartEmpresas.getData().add(dataSeriesEmpresas);
        barChartEmpresas.setPrefSize(400,300);

        HBox hBoxPieChar = new HBox(pieChart);
        hBoxPieChar.setAlignment(Pos.CENTER);
        HBox barCharts = new HBox(barChartDocentes,barChartEmpresas);
        VBox vBoxBarChart = new VBox(20,hBoxPieChar,barCharts);

        Pane root = new Pane(vBoxBarChart);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.show();
    }

    private void showAlert(String headerText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(headerText);
        alert.getDialogPane().setMinWidth(500);
        alert.setContentText(singleton.message);
        alert.show();
    }
}
