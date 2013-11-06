/* * To change this template, choose Tools | Templates * and open the template in the editor. */
package src;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import java.util.*;
/** * @author Administrador */
public class MidletAgenda extends MIDlet implements CommandListener {   
    private RecordStore rs = null;
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
    private Command btnLocalizar;    
    private Command btnExcluir;    
    private Command btnGravar;    
    private Command btnCancelar;    
    private Command btnSair; 
    static final String AGENDA = "db1";
    
    public MidletAgenda(){ 
       openRecStore();//cria o registro
       lista = new List("Agenda", List.IMPLICIT);
       listaContato = new Vector();        
       formCad = new Form("Cadastro");       
       formPesquisa = new Form("Pesquisa");        
       tela = Display.getDisplay(this);               
        //componentes da tela cadastro        
       txtNome = new TextField("Nome:", "", 30, TextField.ANY);       
       txtTelefone = new TextField("Telefone:", "", 30, TextField.ANY);       
       txtPesquisa = new TextField("Pesquisar: ", "", 30, TextField.ANY);       
       btnExcluir = new Command("Excluir", Command.OK, 1);        
       btnAlterar = new Command("Alterar", Command.OK, 2);        
       btnIncluir = new Command("Incluir", Command.OK, 3);        
       btnPesquisar = new Command("Pesquisar", Command.OK, 4);       
       btnLocalizar = new Command("Localizar", Command.OK, 1);               
       btnSair = new Command("Sair", Command.EXIT, 3);                
       //componentes da tela lista        
       btnGravar = new Command("Gravar", Command.OK, 1);        
       btnCancelar = new Command("Cancelar", Command.OK, 2);                
       lista.addCommand(btnIncluir);        
       lista.addCommand(btnAlterar);        
       lista.addCommand(btnExcluir);        
       lista.addCommand(btnPesquisar);        
       lista.addCommand(btnSair);                
       formCad.append(txtNome);        
       formCad.append(txtTelefone);       
       formCad.addCommand(btnGravar);        
       formCad.addCommand(btnCancelar);                 
       lista.setCommandListener(this);       
       formCad.setCommandListener(this);       
       formPesquisa.append(txtPesquisa);        
       formPesquisa.addCommand(btnLocalizar);       
       formPesquisa.setCommandListener(this);    
    }    
    
    private void ExibirLista(){     
        lista.deleteAll();
        for (int i = 0; i < listaContato.size(); i++){        
            lista.append(((Contato)listaContato.elementAt(i)).getNome(), null);
        }      
        tela.setCurrent(lista);    
    }        
    
    private String Pesquisar(){      
        for (int i = 0; i < listaContato.size(); i++){        
            if((((Contato)listaContato.elementAt(i)).getNome()).equals(txtPesquisa.getString())){            
                return ((Contato)listaContato.elementAt(i)).getNome();
            }        
       }          
       return "Não localizado";    
    }
    
    private void openRecStore(){
        try{
            //true é necessário para criar caso o mesmo não exista
            rs = RecordStore.openRecordStore(AGENDA, true);
            
        }
        catch(Exception e){
            db(e.toString());
        }
    }
        
    
    private void writeRecord(String r) {
        byte[] rec = r.getBytes();
        try {
            rs.addRecord(rec, 0, rec.length);
        } 
        catch (Exception e) {
            db(e.toString());
        }
    }
    
    private void db(String st){
        System.err.println("MSG:" + st);
    }
    
    
    private void readRecords() {
        try {
            lista.deleteAll();
            listaContato.removeAllElements();
            byte[] recData = new byte[5];
            int len;
            for (int i = 1; i < rs.getNumRecords() + 1; i++) {
                if (rs.getRecordSize(i) > recData.length) {
                    recData = new byte[rs.getRecordSize(i)];
                }
                len = rs.getRecord(i, recData, 0);
                contato = new Contato();               
                contato.setNome(new String(recData,0,len)); 
                //lista.append("Cod.:["+ i +"]= " + new String(recData,0,len), null);
                listaContato.addElement(contato);
            }
            
            for (int i = 0; i < listaContato.size(); i++){        
             lista.append((i + 1) + " - " + ((Contato)listaContato.elementAt(i)).getNome(), null);
            }      
            tela.setCurrent(lista);     
        } 
        catch (Exception e) {
            db(e.toString());
        }
    }
    
    private void deleteRecStore() {
        if (RecordStore.listRecordStores() != null) {
            try {
                RecordStore.deleteRecordStore(AGENDA);
            } 
            catch (Exception e) {
                db(e.toString());
            }
        }
    }
  
    private void closeRecStore() {
        try {
            rs.closeRecordStore();

        } catch (Exception e) {
            db(e.toString());
        }
    }   

public void startApp() 
{        
    tela.setCurrent(lista);
    readRecords();    
    //closeRecStore();
    //deleteRecStore();
    
}  

public void pauseApp() {    };        
public void destroyApp(boolean unconditional) {    };    
public void commandAction(Command c, Displayable d) {        
    if (c == btnSair){            
        destroyApp(false);
        notifyDestroyed();        
    }        
    if (c == btnIncluir){           
        txtTelefone.setString("");
        txtNome.setString("");
        operacao = 1;
        //Incluir Dados;             
        tela.setCurrent(formCad);                 
    }        
    else if (c == btnAlterar){           
        operacao = 2;
      // Alterar Dados;           
        tela.setCurrent(formCad);           
        txtNome.setString(((Contato)listaContato.elementAt(lista.getSelectedIndex())).getNome());           
        txtTelefone.setString(((Contato)listaContato.elementAt(lista.getSelectedIndex())).getTelefone());        
    }        
    else if (c == btnExcluir){            
        listaContato.removeElementAt(lista.getSelectedIndex());            
        ExibirLista();        
    }        
    else if (c == btnCancelar){            
        tela.setCurrent(lista);        
    }        
    else if( c == btnPesquisar){            
        tela.setCurrent(formPesquisa);        
    }        
    else if( c == btnLocalizar){               
        Alert alerta = new Alert(Pesquisar());                
        tela.setCurrent(alerta,lista);        
    }        
    else if (c == btnGravar){            
        if (operacao == 1){               
            contato = new Contato();               
            contato.setNome(txtNome.getString());               
            contato.setTelefone(txtTelefone.getString());               
            listaContato.addElement(contato);
            writeRecord(contato.getNome());
        }            
        else{               
            ((Contato)listaContato.elementAt(lista.getSelectedIndex())).setNome(txtNome.getString());               
            ((Contato)listaContato.elementAt(lista.getSelectedIndex())).setTelefone(txtTelefone.getString());                          
        }                                
        readRecords();
    }    
  }

} 
