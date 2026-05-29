package servicos;

import entidades.Medico;
import entidades.Paciente;
import java.time.LocalDateTime;

public class Consulta {

    public enum Status {
        AGENDADA,
        REALIZADA,
        CANCELADA
    }

    private Medico medico;
    private Paciente paciente;
    private LocalDateTime dataHora;
    private Status status;

    private String diagnostico;
    private String prescricao;

    public Consulta(Medico medico, Paciente paciente, LocalDateTime dataHora) {
        this.medico = medico;
        this.paciente = paciente;
        this.dataHora = dataHora;
        this.status = Status.AGENDADA;
        this.diagnostico = "";
        this.prescricao = "";
    }

    public Medico getMedico() { return medico; }
    public Paciente getPaciente() { return paciente; }
    public LocalDateTime getDataHora() { return dataHora; }
    public Status getStatus() { return status; }
    public String getDiagnostico() { return diagnostico; }
    public String getPrescricao() { return prescricao; }

    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public void cancelar() {
        if (this.status == Status.REALIZADA) {
            System.out.println("Não é possível cancelar uma consulta já realizada.");
            return;
        }
        this.status = Status.CANCELADA;
    }

    public void realizarConsulta(String diagnostico, String prescricao) {
        if (this.status == Status.CANCELADA) {
            System.out.println("Não é possível realizar uma consulta cancelada.");
            return;
        }
        this.diagnostico = diagnostico;
        this.prescricao = prescricao;
        this.status = Status.REALIZADA;
    }

    @Override
    public String toString() {
        return String.format(
            "Consulta{paciente=%s, medico=%s, data=%s, status=%s}",
            paciente.getNome(), medico.getNome(), dataHora, status
        );
    }
}