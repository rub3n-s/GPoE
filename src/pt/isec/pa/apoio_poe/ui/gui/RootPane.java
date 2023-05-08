package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.ModelManager;
import pt.isec.pa.apoio_poe.ui.gui.candidatura.CandidaturaFechadaUI;
import pt.isec.pa.apoio_poe.ui.gui.candidatura.CandidaturaUI;
import pt.isec.pa.apoio_poe.ui.gui.configuracao.ConfiguracaoFechadaUI;
import pt.isec.pa.apoio_poe.ui.gui.configuracao.ConfiguracaoUI;
import pt.isec.pa.apoio_poe.ui.gui.consulta.ConsultaUI;
import pt.isec.pa.apoio_poe.ui.gui.orientadores.OrientadoresUI;
import pt.isec.pa.apoio_poe.ui.gui.proposta.PropostaFechadaUI;
import pt.isec.pa.apoio_poe.ui.gui.proposta.PropostaUI;

public class RootPane extends BorderPane {
    ModelManager model;

    public RootPane(ModelManager model) {
        this.model = model;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        StackPane stackPane = new StackPane(
                new ConfiguracaoUI(model), new CandidaturaUI(model), new PropostaUI(model), new OrientadoresUI(model), new ConsultaUI(model),
                new ConfiguracaoFechadaUI(model), new CandidaturaFechadaUI(model), new PropostaFechadaUI(model));

        this.setTop(new VBox(new AppMenu(model)));
        this.setCenter(stackPane);
    }

    private void registerHandlers() { }

    private void update() { }
}
