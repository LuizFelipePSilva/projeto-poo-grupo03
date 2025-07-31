package br.com.grupo03.projetopoo.model.service;

import br.com.grupo03.projetopoo.model.entity.Tipo;
import br.com.grupo03.projetopoo.model.dao.TipoDAO;
import br.com.grupo03.projetopoo.model.service.execption.TipoServiceException; // 1. IMPORTAR a nova exceção
import br.com.grupo03.projetopoo.model.service.interfaces.TipoInterfaceService;

import java.util.List;

public class TipoService implements TipoInterfaceService {

    private final TipoDAO tipoDAO = new TipoDAO();

    @Override
    public Tipo getByID(Long id) {
        return tipoDAO.findById(id);
    }

    @Override
    public Tipo getByName(String name) {
        return tipoDAO.findByName(name);
    }

    @Override
    public void register(Tipo tipo) {
        if (tipoDAO.findByName(tipo.getNome()) != null) {
            // 2. APLICAR a exceção personalizada
            throw new TipoServiceException("Tipo já cadastrado com este nome.");
        }
        tipoDAO.save(tipo);
    }

    @Override
    public void remover(Long id) {
        Tipo tipoExistente = tipoDAO.findById(id);
        if (tipoExistente == null) {
            // 2. APLICAR a exceção personalizada
            throw new TipoServiceException("Tipo não encontrado para remoção com o ID: " + id);
        }
        tipoDAO.delete(tipoExistente);
    }

    @Override
    public Tipo updateNome(String novoNome, Long id) {
        Tipo tipoExistente = tipoDAO.findById(id);
        if (tipoExistente == null) {
            // 2. APLICAR a exceção personalizada
            throw new TipoServiceException("Tipo não encontrado para atualização com o ID: " + id);
        }
        // Validação extra: verificar se o novo nome já não está em uso por outro Tipo
        Tipo tipoComMesmoNome = tipoDAO.findByName(novoNome);
        if (tipoComMesmoNome != null && !tipoComMesmoNome.getId().equals(id)) {
            throw new TipoServiceException("O nome '" + novoNome + "' já está em uso por outro tipo.");
        }

        tipoExistente.setNome(novoNome);
        tipoDAO.update(tipoExistente);
        return tipoExistente;
    }

    @Override
    public List<Tipo> getAll() {
        return tipoDAO.findAll();
    }

    /**
     * ✅ Salva um novo tipo usando o DAO diretamente (sem EntityManager manual)
     */
    public void saveTipo(Tipo tipo) {
        if (tipoDAO.findByName(tipo.getNome()) != null) {
            // 2. APLICAR a exceção personalizada
            throw new TipoServiceException("Tipo já existe com este nome.");
        }
        tipoDAO.save(tipo); // O DAO gerencia a persistência
    }

    public void update(Tipo tipo) {
        if (tipoDAO.findById(tipo.getId()) == null) {
            // 2. APLICAR a exceção personalizada
            throw new TipoServiceException("Tipo não encontrado para atualização com o ID: " + tipo.getId());
        }
        tipoDAO.update(tipo);
    }
}