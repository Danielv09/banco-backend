package com.prueba.banco.service.impl;

import com.prueba.banco.dto.ProductRequest;
import com.prueba.banco.dto.ProductResponse;
import com.prueba.banco.entity.Product;
import com.prueba.banco.entity.Client;
import com.prueba.banco.enums.EstadoCuenta;
import com.prueba.banco.repository.ProductRepository;
import com.prueba.banco.repository.ClientRepository;
import com.prueba.banco.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public ProductServiceImpl(ProductRepository productRepository, ClientRepository clientRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public ProductResponse create(ProductRequest req) {
        Client cliente = clientRepository.findById(req.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        Product product = new Product();
        product.setTipoCuenta(req.getTipoCuenta());
        product.setSaldo(req.getSaldo());
        product.setExentaGMF(req.getExentaGMF());
        product.setCliente(cliente);



        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    @Override
    public List<ProductResponse> list() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        return toResponse(product);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest req) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        product.setTipoCuenta(req.getTipoCuenta());
        product.setSaldo(req.getSaldo());
        product.setExentaGMF(req.getExentaGMF());
        product.setCliente(clientRepository.findById(req.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado")));

        Product updated = productRepository.save(product);
        return toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse cambiarEstado(Long id, String nuevoEstado) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        product.setEstado(EstadoCuenta.valueOf(nuevoEstado.toUpperCase()));
        Product updated = productRepository.save(product);
        return toResponse(updated);
    }

    private ProductResponse toResponse(Product product) {
        ProductResponse res = new ProductResponse();
        res.setId(product.getId());
        res.setTipoCuenta(product.getTipoCuenta());
        res.setNumeroCuenta(product.getNumeroCuenta());
        res.setEstado(product.getEstado());
        res.setSaldo(product.getSaldo());
        res.setExentaGMF(product.getExentaGMF());
        res.setFechaCreacion(product.getFechaCreacion());
        res.setFechaModificacion(product.getFechaModificacion());
        res.setClienteId(product.getCliente().getId());
        return res;
    }
}