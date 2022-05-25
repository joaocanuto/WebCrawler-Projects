public class Iphone {
    private String title;
    private double valor;
    private String endereço;
    private String urlAnuncio;

    @Override
    public String toString() {
        return "title="  + title + '\n' +
                "valor=" + valor + '\n' +
                "endereço='" + endereço + '\n' +
                "urlAnuncio='" + urlAnuncio + '\n';
    }

    public Iphone(String title, double valor, String endereço, String urlAnuncio) {
        this.title = title;
        this.valor = valor;
        this.endereço = endereço;
        this.urlAnuncio = urlAnuncio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }

    public String getUrlAnuncio() {
        return urlAnuncio;
    }

    public void setUrlAnuncio(String urlAnuncio) {
        this.urlAnuncio = urlAnuncio;
    }
}
