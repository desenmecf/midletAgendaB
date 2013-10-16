/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author aluno
 */
public class Contato {
    private String nome;
    private String telefone;
    private String email;
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome(){
        return nome;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    public String getTelefone(){
        return telefone;
    }    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
}
