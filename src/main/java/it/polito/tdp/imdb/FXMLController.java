/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.RegistiAdiacenti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	int anno=this.boxAnno.getValue();
    	this.model.creaGrafo(anno);
    	this.boxRegista.getItems().addAll(this.model.getGrafo().vertexSet());
    	this.txtResult.appendText("Grafo creato con "+this.model.getGrafo().vertexSet().size()+" vertici e "+this.model.getGrafo().edgeSet().size()+" archi");
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	this.txtResult.clear();
    	Director director=this.boxRegista.getValue();
    	for(RegistiAdiacenti r:this.model.getRegistiAdiacenti(director))
    		this.txtResult.appendText(r.toString()+"\n");
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	this.txtResult.clear();
    	Director director=this.boxRegista.getValue();
    	if(director==null) {
    		this.txtResult.setText("Selezionare un regista");
    		return;
    	}
    	int attoriCondivisi=Integer.parseInt(this.txtAttoriCondivisi.getText());
    	this.txtResult.appendText("Cammino: "+"\n");
    	for(Director d: this.model.trovaPercorso(attoriCondivisi, director)) {
    		this.txtResult.appendText(d.toString()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	Integer [] anni= {2004,2005,2006};
    	this.boxAnno.getItems().addAll(anni);
    	
    }
    
}
