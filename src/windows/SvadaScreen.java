package windows;

import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class SvadaScreen {
	SvadaScreen(){
		System.out.println("HEU");
        Text t = new Text("Viktig brukeravtale mellom Calendar AS og Studass\n\n§1\nI og med en kostnadseffektiv ressursbruk stabiliseres relasjonene med sikte på beskaffenheten. På grunn av en manglende treffsikkerhet kommuniseres spisskompetansen avhengig av visjonen. I henhold til en tverrfaglig overveielse effektueres kriteriene parallelt med behovene. Vedrørende en implisitt effekt maksimeres innsatsen eller sagt på en annen måte: implementeringen. Sammenholdt med en vesentlig implementering effektueres kunnskapene eller sagt på en annen måte: konseptet. I henhold til en manglende mobilitet konkretiseres resultatene hva angår egenarten. Uavhengig av en gjeldende implementering realiseres risikofaktorene gjennom konsekvensaspektet. Med tanke på en implisitt overveielse balanseres innsatsen i motsetning til en samlet vurdering. Sett hen til en avtagende treffsikkerhet styrkes tilstedeværelsen med sikte på beskaffenheten.\n\n§2\nForutsatt en ikke ubetydelig styringsinnsats styrkes betydningen i relasjon til konsekvensaspektet. Uavhengig av en bærekraftig avveining genereres scenarioet med henblikk på vesentligheten. I betraktning av en kommunikativ implementering revitaliseres innsatsen avhengig av parametrene. Sett hen til en avtagende kvalitetssikring realiseres instrumentet som en følge av egenarten. Sett hen til en kostnadseffektiv innovasjon forankres instrumentet ut fra visjonen. Etter en totalvurdering av en kostnadseffektiv organisasjon spores insentivene med sikte på visjonen.\n\n§3\nGrunnet en manglende kvalitetssikring stabiliseres potensialet innenfor rammen av føringene. På grunnlag av en kommunikativ treffsikkerhet kommuniseres risikofaktorene utenom føringene. I betraktning av en helhetlig organisasjon initieres strategien på samme måte som evalueringen. Uten hensyn til en manglende tidshorisont stabiliseres tilstedeværelsen med henblikk på konsekvensaspektet. Under henvisning til en ikke ubetydelig effektivisering identifiseres kunnskapene i tillegg til parametrene. Med utgangspunkt i en langsiktig implementering styrkes betydningen gjennom evalueringen. Sett på bakgrunn av en langsiktig struktur maksimeres relasjonene hva angår implementeringen. \nLes mer på ");
        TextFlow textFlow = new TextFlow(t);
        textFlow.setMaxWidth(500);
        textFlow.setPadding(new Insets(30));
        Pane p = new Pane();
		p.setMaxWidth(500);

		p.getChildren().add(textFlow);
         
        Scene secondScene = new Scene(p, 500, 700);
        Hyperlink link = new Hyperlink("svadagenerator.no");
        URL i;
        try {
			i = new URL("http://www.svadagenerator.no/");
			link.setOnAction(e -> openWebpage(i));
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        textFlow.getChildren().add(link);
        
        Stage secondStage = new Stage();
        secondStage.setTitle("BrukerAvtale");
        secondStage.setScene(secondScene);
         
        //Set position of second window, related to primary window.
      
        secondStage.show();
	}
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public static void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
}
