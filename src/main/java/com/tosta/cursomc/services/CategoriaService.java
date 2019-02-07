package com.tosta.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tosta.cursomc.domain.Categoria;
import com.tosta.cursomc.repository.CategoriaRepository;
import com.tosta.cursomc.services.exceptions.DataIntegrityException;
import com.tosta.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) throws ObjectNotFoundException {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) throws ObjectNotFoundException {
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		try {
			repo.deleteById(id);			
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível escluir uma categoria que possui produtos");
		}
	}
}
