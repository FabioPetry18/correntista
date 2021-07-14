/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.controle;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PerfilDao;
import br.com.correntista.dao.PerfilDaoImpl;

import br.com.correntista.dao.UsuarioDao;
import br.com.correntista.dao.UsuarioDaoImpl;
import br.com.correntista.entidade.Perfil;
import br.com.correntista.entidade.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

/**
 *
 * @author fabio
 */
@ManagedBean(name = "usuarioC")
@ViewScoped 
public class UsuarioControle {

    private Usuario usuario;
    private Perfil perfil;
    private UsuarioDao usuarioDao;
    private Session sessao;
    private List<Usuario> usuarios;
    private List<SelectItem> comboPerfis;
    private DataModel<Usuario> modelUsuarios;
    private int aba;

    public UsuarioControle() {

        usuarioDao = new UsuarioDaoImpl();
    }

    public void salvar() {
        sessao = HibernateUtil.abrirSessao();
        try {
            usuarioDao.salvarOuAlterar(usuario, sessao);
            usuario = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso!", null));
             modelUsuarios = null;
        } catch (HibernateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informação", ""));

        } finally {

        }
    }

    public void pesquisarPorNome() {
        sessao = HibernateUtil.abrirSessao();
        try {
            usuarios = usuarioDao.pesquisarPorNome(usuario.getNome(), sessao);
            modelUsuarios = new ListDataModel<>(usuarios);
            usuario.setNome(null);
        } catch (HibernateException e) {
            System.out.println("Erro ao pesquisar " + e.getMessage());
        } finally {
            sessao.close();
        }

    }
    
    public void carregarComboPerfil(){
         sessao = HibernateUtil.abrirSessao();
         PerfilDao perfilDao = new PerfilDaoImpl();
         try {
            List<Perfil> perfis = perfilDao.pesquisarTodos(sessao);
            comboPerfis = new ArrayList<>();
            for(Perfil perfi : perfis){
                comboPerfis.add(new SelectItem(perfi.getId(),perfi.getNome()));
            }
            
        } catch (HibernateException e) {
             System.out.println("Erro ao carregar combobox perfil " + e.getMessage());
        }finally{
             sessao.close();
         }
    }
    public void onTabChange(TabChangeEvent event) {
       if(event.getTab().getTitle().equals("Novo")){
           carregarComboPerfil();
       }
    }

    public void onTabClose(TabCloseEvent event) {
        
    }
    
    public void excluir(){
        usuario = modelUsuarios.getRowData();
           sessao = HibernateUtil.abrirSessao();
            usuario = null;
        try {
         usuarioDao.excluir(usuario, sessao);
            usuario = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Excluido com sucesso!", null));
            modelUsuarios = null;
        } catch (HibernateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao Excluir", ""));

        }
    }
    
    public void prepararAlterar(){
        usuario = modelUsuarios.getRowData();
        aba = 1;
    }
    
    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Perfil getPerfil() {
        if(perfil == null){
            perfil = new Perfil();
        }
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    
    public DataModel<Usuario> getModelUsuarios() {
        return modelUsuarios;
    }

    public int getAba() {
        return aba;
    }

    public List<SelectItem> getComboPerfis() {
        return comboPerfis;
    }
    
}
