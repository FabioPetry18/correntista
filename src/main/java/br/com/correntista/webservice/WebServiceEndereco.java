/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.webservice;

import br.com.correntista.entidade.Endereco;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;

/**
 *
 * @author fabio
 */
public class WebServiceEndereco {

    private Client client;
    private WebResource webResource;

    public WebServiceEndereco() {
        ClientConfig clientConfig = new DefaultClientConfig(GensonProvider.class);
        client = Client.create(clientConfig);
        //Utilizado para imprimir as operacoes no console
        client.addFilter(new LoggingFilter(System.out));
        webResource = client.resource("https://viacep.com.br/ws/");
    }
    public Endereco pesquisarCep(String cep){
        
        
        return webResource.path(cep).path("/json").get(Endereco.class);
                
    }
  //  public static void main(String[] args) {
    //    WebServiceEndereco webService = new WebServiceEndereco();
      //  Endereco end = webService.pesquisarCep("88049200");
        //if (end != null) {
          //  System.err.println("Log: " + end.getLogradouro());
            //System.err.println("Log: " + end.getBairro()); TESTE
            //System.err.println("Log: " + end.getLocalidade());
        //}
  //  }
}
