/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.*;

/**
 * @author Administrador
 */
public class AgendaTel extends MIDlet implements CommandListener {
    Display tela;
    Form formCad;
    Form formPesquisa;
    Vector listaContato;
    List lista;
    private int operacao;
    private Contato contato;
    private TextField txtNome;
    private TextField txtTelefone;
    private TextField txtPesquisa;
    private Command btnIncluir;
    private Command btnAlterar;
    private Command btnPesquisar;
    private Command btnBuscaContato;
    private Command btnExcluir;
    private Command btnGravar;
    private Command btnCancelar;
    private Command btnSair;    
    
    public AgendaTel(){
        
        lista = new List("Agenda", List.IMPLICIT);
        listaContato = new Vector();
        formCad = new Form("Cadastro");
        formPesquisa = new Form("Pesquisa");
        tela = Display.getDisplay(this);
        
        //componentes da tela cadastro
        txtNome = new TextField("Nome:", "", 30, TextField.ANY);
        txtTelefone = new TextField("Telefone:", "", 30, TextField.ANY);
        btnExcluir = new Command("Excluir", Command.OK, 1);
        btnAlterar = new Command("Alterar", Command.OK, 2);
        btnPesquisar = new Command("Pesquisar", Command.OK, 3);
        btnBuscaContato = new Command("Buscar Contato", Command.OK, 3);
        btnIncluir = new Command("Incluir", Command.OK, 3); 
        txtPesquisa = new TextField("Nome do Contato:", "", 30, TextField.ANY);
        
        btnSair = new Command("Sair", Command.EXIT, 3);
        
        //componentes da tela lista
        btnGravar = new Command("Gravar", Command.OK, 1);
        btnCancelar = new Command("Cancelar", Command.OK, 2);
        
        lista.addCommand(btnIncluir);
        lista.addCommand(btnAlterar);
        lista.addCommand(btnPesquisar);
        lista.addCommand(btnExcluir);
        lista.addCommand(btnSair);
        
        formCad.append(txtNome);
        formCad.append(txtTelefone);
        formCad.addCommand(btnGravar);
        formCad.addCommand(btnCancelar); 
        
        formPesquisa.append(txtPesquisa);
        formPesquisa.addCommand(btnBuscaContato);
        formPesquisa.addCommand(btnCancelar);
        
        
        lista.setCommandListener(this);
        formCad.setCommandListener(this);
        formPesquisa.setCommandListener(this);
    }

    private void ExibirLista(){
      lista.deleteAll(); 
      for (int i = 0; i < listaContato.size(); i++){
         lista.append("Nome: " + ((Contato)listaContato.elementAt(i)).getNome() + "\nTel.: " + ((Contato)listaContato.elementAt(i)).getTelefone(), null);
      }
      tela.setCurrent(lista);
    }
    

    public void startApp() {
        tela.setCurrent(lista);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == btnSair){
            destroyApp(false);
            notifyDestroyed();
        }
        if (c == btnIncluir){
            txtTelefone.setString("");   
            txtNome.setString(""); 
            operacao = 1;//Incluir Dados; 
            tela.setCurrent(formCad);         
        }
        else if (c == btnAlterar){
           operacao = 2; // Alterar Dados;
           tela.setCurrent(formCad);
           txtNome.setString(((Contato)listaContato.elementAt(lista.getSelectedIndex())).getNome());
           txtTelefone.setString(((Contato)listaContato.elementAt(lista.getSelectedIndex())).getTelefone());
        }
        if (c == btnPesquisar){
            tela.setCurrent(formPesquisa);
            txtNome.setString("");
        }
        else if (c == btnExcluir){
            listaContato.removeElementAt(lista.getSelectedIndex());
            ExibirLista();
        }
        else if (c == btnCancelar){
            tela.setCurrent(lista);
        }
        else if (c == btnGravar){
            if (operacao == 1){
               contato = new Contato();
               contato.setNome(txtNome.getString());
               contato.setTelefone(txtTelefone.getString());
               listaContato.addElement(contato);
            }
            else{
               ((Contato)listaContato.elementAt(lista.getSelectedIndex())).setNome(txtNome.getString());
               ((Contato)listaContato.elementAt(lista.getSelectedIndex())).setTelefone(txtTelefone.getString());              
            }                
            ExibirLista();            
        }
    }
}
