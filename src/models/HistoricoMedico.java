/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.util.List;

public class HistoricoMedico {
    private boolean fuma;
    private boolean bebe;
    private boolean colesterol;
    private boolean diabete;
    private boolean doencaCardiaca;

    private List<String> cirurgias;
    private List<String> alergias;

    public HistoricoMedico() {
        this.cirurgias = new ArrayList<>();
        this.alergias = new ArrayList<>();
    }

    public boolean getFuma() {
        return this.fuma;
    }

    public void setFuma(boolean fuma) {
        this.fuma = fuma;
    }

    public boolean getBebe() {
        return this.bebe;
    }

    public void setBebe(boolean bebe) {
        this.bebe = bebe;
    }

    public boolean getColesterol() {
        return this.colesterol;
    }

    public void setColesterol(boolean colesterol) {
        this.colesterol = colesterol;
    }

    public boolean getDiabete() {
        return this.diabete;
    }

    public void setDiabete(boolean diabete) {
        this.diabete = diabete;
    }

    public boolean getDoencaCardiaca() {
        return this.doencaCardiaca;
    }

    public void setDoencaCardiaca(boolean doencaCardiaca) {
        this.doencaCardiaca = doencaCardiaca;
    }

    public void adicionarCirurgia(String cirurgia) {
        this.cirurgias.add(cirurgia);
    }

    public void removerCirurgia(String cirurgia) {
        if (this.cirurgias.contains(cirurgia)) {
            this.cirurgias.remove(cirurgia);
        } else {
            System.out.println("Cirurgia não encontrada");
        }
    }

    public List<String> getCirurgias() {
        return new ArrayList<>(this.cirurgias);
    }


    public void adicionarAlergia(String alergia) {
        this.alergias.add(alergia);
    }

    public void removerAlergia(String alergia) {
        if (this.alergias.contains(alergia)) {
            this.alergias.remove(alergia);
        } else {
            System.out.println("Alergia não encontrada");
        }
    }

    public List<String> getAlergias() {
        return new ArrayList<>(this.alergias);
    }
}
