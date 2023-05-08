package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ManagementData;

public enum ManagementState {
    CONFIGURACAO,
    CONFIGURACAO_FECHADA,
    CANDIDATURA,
    CANDIDATURA_FECHADA,
    PROPOSTA,
    PROPOSTA_FECHADA,
    ORIENTADORES,
    ORIENTADORES_FECHADA,
    CONSULTA;

    IManagementState createState(ManagementContext context, ManagementData data) {
        return switch (this) {
            case CONFIGURACAO -> new ConfiguracaoState(context,data);
            case CANDIDATURA -> new CandidaturaState(context,data);
            case PROPOSTA -> new PropostaState(context,data);
            case ORIENTADORES -> new OrientadoresState(context,data);
            case CONSULTA -> new ConsultaState(context,data);

            case CONFIGURACAO_FECHADA -> new ConfiguracaoFechadaState(context,data);
            case CANDIDATURA_FECHADA -> new CandidaturaFechadaState(context,data);
            case PROPOSTA_FECHADA -> new PropostaFechadaState(context,data);
            case ORIENTADORES_FECHADA -> new OrientadoresFechadaState(context,data);
        };
    }
}
