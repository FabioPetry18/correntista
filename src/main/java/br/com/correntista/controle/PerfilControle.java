/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.controle;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PerfilDao;
import br.com.correntista.dao.PerfilDaoImpl;
import br.com.correntista.entidade.Perfil;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;



/**
 *
 * @author fabio
 */
@ManagedBean(name = "PerfilC")
public class PerfilControle {
    
    private PerfilDao perfilDao;
    private Perfil perfil;
     private List<Perfil> perfis;
     
     
     public String salvar() {
     perfilDao = new PerfilDaoImpl();
     Session  Sessao = HibernateUtil.abrirSessao();
     FacesContext contexto = FacesContext.getCurrentInstance(); //perguntar
     FacesMessage message = null;                               //perguntar
      try {
          perfilDao.salvarOuAlterar(perfil, Sessao);
          message = new FacesMessage("salvo com sucesso!","");
          contexto.addMessage(null, message);
            
        } catch (HibernateException e) {
            message = new FacesMessage("erro ao salvar!","");
            contexto.addMessage(null, message);
        }finally{
             Sessao.close();
             contexto.addMessage(null, message);
        }
        return null;
        
       
        
        
    }
        
     public void pesquisarPorNome() {
       Session Sessao = HibernateUtil.abrirSessao();
        try {
          perfis   = perfilDao.pesquisarPorNome(perfil.getNome(), Sessao);
            perfil  .setNome(null);
        } catch (HibernateException e) {
            System.out.println("Erro ao pesquisar " + e.getMessage());
        } finally {
            Sessao.close();
        }

    }
     
      public Perfil getPerfil() {
        if(perfil == null) {
            perfil = new Perfil();  
        }
        return perfil;
    }

    public void setPessoaFisica(Perfil perfil) {
        this.perfil = perfil;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }
   
}
