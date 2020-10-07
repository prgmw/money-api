package br.com.money.mapper;

import org.mapstruct.Mapper;

import br.com.money.http.CategoriaDTO;
import br.com.money.model.Categoria;

@Mapper(componentModel="spring")
public interface CategoriaMapper {
	
	Categoria categoriaDTOToCategoria(CategoriaDTO categoriaDto);
	CategoriaDTO categoriaToCategoriaDTO(Categoria categoria);
	
}
