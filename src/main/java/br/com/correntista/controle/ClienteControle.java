/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.controle;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PessoaFisicaDao;
import br.com.correntista.dao.PessoaFisicaDaoImpl;
import br.com.correntista.entidade.Cliente;
import br.com.correntista.entidade.PessoaFisica;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;




/**
 *
 * @author fabio
 */
@ManagedBean(name = "clienteC")
public class ClienteControle {
    
    private PessoaFisica pessoaFisica;
    private PessoaFisicaDao fisicaDao;
    
    public String salvar() {
        fisicaDao = new PessoaFisicaDaoImpl();
        Session sessao = HibernateUtil.abrirSessao();
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage message = null;
        try {
          fisicaDao.salvarOuAlterar(pessoaFisica, sessao);
          message = new FacesMessage("salvo com sucesso!","");
          contexto.addMessage(null, message);
            
        } catch (HibernateException e) {
            message = new FacesMessage("erro ao salvar!","");
            contexto.addMessage(null, message);
        }finally{
             sessao.close();
             contexto.addMessage(null, message);
        }
        
       
        return "mostra";
        
    }

    
    
    
    public PessoaFisica getPessoaFisica() {
        if(pessoaFisica == null) {
            pessoaFisica = new PessoaFisica();  
        }
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }
    
    
    
    
    
    
    
    
    
   
    
        
    
    
    
}
