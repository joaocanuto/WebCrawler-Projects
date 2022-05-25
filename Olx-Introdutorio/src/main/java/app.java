import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;
import sun.security.ec.point.ImmutablePoint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class app {
    public static void main(String [] args) throws IOException {
        //Setando os URL's
        String url1_page1="https://go.olx.com.br/grande-goiania-e-anapolis?q=iphone%2011";
        String url2_page2="https://go.olx.com.br/grande-goiania-e-anapolis?o=2&q=iphone%2011";
        String url3_page3="https://go.olx.com.br/grande-goiania-e-anapolis?o=3&q=iphone%2011";

        //Montando nossa lista de iphones:
        ArrayList<Iphone> ListadeIphones = new ArrayList<>();

//        ListadeIphones.addAll(GerandoLista(url1_page1, ListadeIphones));
//        ListadeIphones.addAll(GerandoLista(url2_page2, ListadeIphones));
//        ListadeIphones.addAll(GerandoLista(url3_page3, ListadeIphones));
        //System.out.println(GerandoLista(url1_page1, ListadeIphones));
        GerandoLista(url1_page1, ListadeIphones);
        GerandoLista(url2_page2, ListadeIphones);
        GerandoLista(url3_page3, ListadeIphones);
        ArrayList<Iphone> iphonesBaratos = formatandoAListaIphones(ListadeIphones, calcMedia(ListadeIphones));
        System.out.println("Média = "+ calcMedia(ListadeIphones));
//        for(Iphone aux : iphonesBaratos){
//            System.out.println(aux);
//        }
        fileUtils(iphonesBaratos);

    }
    public static void GerandoLista(String url, ArrayList<Iphone> lista) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements anuncios = doc.getElementsByAttributeValueContaining("class", "sc-1fcmfeb-2 fvbmlV");
        //para cada anuncio dentro de anuncios
        for(Element anuncio : anuncios){
            Elements aux = anuncio.getElementsByTag("a");
            String nome = anuncio.getElementsByAttributeValueContaining("class", "kgl1mq-0 iYdPim sc-bdVaJa daxpJj").html();
            String valor = anuncio.getElementsByAttributeValueContaining("class", "m7nrfa-0 cjhQnm sc-bdVaJa cpfGxa").html();
            String valorFormatado = valor.replaceAll("[^0-9]", "");
            String endereco = anuncio.getElementsByAttributeValueContaining("class", "sc-1c3ysll-1 flPYFW sc-bdVaJa bxVNCd").text();
            String linkUrl = aux.attr("href");

            if(!valorFormatado.isEmpty() && nome != null){
                String nomeAux = nome.substring(0,9);
                Iphone iphoneAux = new Iphone(nomeAux, Double.parseDouble(valorFormatado), endereco, linkUrl);
                lista.add(iphoneAux);
            }
        }
        //System.out.println(lista);
    }

    public static double calcMedia(ArrayList<Iphone> lista){
        double media = 0, soma = 0;
        for(Iphone iphone : lista){
            soma += iphone.getValor();
        }
        return soma/lista.size();
    }
    public static ArrayList<Iphone> formatandoAListaIphones(ArrayList<Iphone> lista, double media){
        ArrayList<Iphone> listaAux = new ArrayList<>();
        for(Iphone iphoneAux : lista){
            if(iphoneAux.getValor() < media){
                listaAux.add(iphoneAux);
            }
        }
        return listaAux;
    }

    static String path = "iphoneSheets.csv";

    public static void fileUtils(ArrayList<Iphone> lista) throws IOException {
        FileWriter fw = new FileWriter(new File(path));
        CSVWriter cw = new CSVWriter(fw);

        String[] headers = {"titulo","valor","endereço","url"};
        ArrayList<String[]> data = new ArrayList<>();
        data.add(headers);

        for(Iphone i : lista){
            String[] item = {i.getTitle(),String.valueOf(i.getValor()),i.getEndereço(),i.getUrlAnuncio()};
            data.add(item);
        }

        cw.writeAll(data);
        cw.close();
        fw.close();
    }
}
