package br.com.grupo03.projetopoo.model.service;
import br.com.grupo03.projetopoo.entity.Tipo;
import br.com.grupo03.projetopoo.model.dao.TipoDAO;
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
            throw new RuntimeException("Tipo já cadastrado com este nome.");
        }
        tipoDAO.save(tipo);
    }

    @Override
    public void remover(Long id) {
        Tipo tipoExistente = tipoDAO.findById(id);
        if (tipoExistente == null) {
            throw new RuntimeException("Tipo não encontrado para remoção.");
        }
        tipoDAO.delete(tipoExistente);
    }

    @Override
    public Tipo updateNome(String novoNome, Long id) {
        Tipo tipoExistente = tipoDAO.findById(id);
        if (tipoExistente == null) {
            throw new RuntimeException("Tipo não encontrado para atualização.");
        }
        tipoExistente.setNome(novoNome);
        tipoDAO.update(tipoExistente);
        return tipoExistente;
    }

    @Override
    public List<Tipo> getAll() {
        return tipoDAO.findAll();
    }
}
