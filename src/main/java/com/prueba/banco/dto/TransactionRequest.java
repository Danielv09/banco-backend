package com.prueba.banco.dto;

import com.prueba.banco.enums.TransactionType;
import java.math.BigDecimal;

public class TransactionRequest {

    // Tipo de transacción: CONSIGNACION, RETIRO, TRANSFERENCIA
    private TransactionType tipo;

    // Monto de la transacción
    private BigDecimal monto;

    // ID de la cuenta origen (solo aplica en RETIRO y TRANSFERENCIA)
    private Long cuentaOrigenId;

    // ID de la cuenta destino (solo aplica en CONSIGNACION y TRANSFERENCIA)
    private Long cuentaDestinoId;

    // --- Getters y Setters completos ---
    public TransactionType getTipo() { return tipo; }
    public void setTipo(TransactionType tipo) { this.tipo = tipo; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public Long getCuentaOrigenId() { return cuentaOrigenId; }
    public void setCuentaOrigenId(Long cuentaOrigenId) { this.cuentaOrigenId = cuentaOrigenId; }

    public Long getCuentaDestinoId() { return cuentaDestinoId; }
    public void setCuentaDestinoId(Long cuentaDestinoId) { this.cuentaDestinoId = cuentaDestinoId; }
}