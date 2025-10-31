# API Documentation

## Overview
This document describes the REST APIs available in the Document Request Management System.

## Base URL
All API endpoints are relative to: `/api`

## Authentication
All API endpoints require authentication via session cookie. Users must be logged in to access these endpoints.

## Endpoints

### Solicitações (Document Requests)

#### List All Requests
```
GET /solicitacoes
```
**Response:**
```json
{
  "solicitacoes": [
    {
      "id": 1,
      "tipoDocumento": {
        "id": 1,
        "nome": "Histórico Escolar"
      },
      "status": "PENDENTE",
      "dataSolicitacao": "2025-10-31T10:00:00",
      "prioridade": "ALTA",
      "usuario": {
        "id": 1,
        "nome": "João Silva"
      }
    }
  ]
}
```

#### Create New Request
```
POST /solicitacoes
```
**Request Body:**
```json
{
  "idAluno": 1,
  "idDocumentoTipo": 1,
  "urgencia": "ALTA",
  "observacoes": "Necessário para matrícula"
}
```
**Response:**
```json
{
  "id": 1,
  "mensagem": "Solicitação criada com sucesso"
}
```

#### Get Request Details
```
GET /solicitacoes/{id}
```
**Response:**
```json
{
  "id": 1,
  "tipoDocumento": {
    "id": 1,
    "nome": "Histórico Escolar"
  },
  "status": "PENDENTE",
  "dataSolicitacao": "2025-10-31T10:00:00",
  "prioridade": "ALTA",
  "usuario": {
    "id": 1,
    "nome": "João Silva"
  },
  "historicos": [
    {
      "id": 1,
      "status": "PENDENTE",
      "dataMudanca": "2025-10-31T10:00:00",
      "observacao": "Solicitação criada"
    }
  ]
}
```

#### Update Request Status
```
PUT /solicitacoes/{id}/status
```
**Request Body:**
```json
{
  "status": "EM_ANDAMENTO",
  "observacao": "Em processamento",
  "idAdmin": 2
}
```
**Response:**
```json
{
  "mensagem": "Status atualizado com sucesso"
}
```

### Tipos de Documento (Document Types)

#### List All Document Types
```
GET /documentos
```
**Response:**
```json
{
  "tipos": [
    {
      "id": 1,
      "nome": "Histórico Escolar",
      "descricao": "Documento com histórico acadêmico completo"
    }
  ]
}
```

#### Get Document Type Details
```
GET /documentos/{id}
```
**Response:**
```json
{
  "id": 1,
  "nome": "Histórico Escolar",
  "descricao": "Documento com histórico acadêmico completo",
  "prazoMedio": 5
}
```

## WebSocket API

### Notifications Endpoint
```
WebSocket: /websocket/notificacoes
```

### Message Format
```json
{
  "tipo": "ATUALIZACAO_STATUS",
  "dados": {
    "idSolicitacao": 1,
    "novoStatus": "EM_ANDAMENTO",
    "mensagem": "Sua solicitação está em processamento"
  }
}
```

### Event Types
1. `NOVA_SOLICITACAO`: Sent when a new request is created
2. `ATUALIZACAO_STATUS`: Sent when a request status is updated
3. `NOTIFICACAO_SISTEMA`: Sent for system-wide notifications

## Error Handling

### Error Response Format
```json
{
  "erro": true,
  "mensagem": "Descrição do erro",
  "codigo": "ERRO_001",
  "detalhes": {
    "campo": "descricao do erro no campo"
  }
}
```

### Common Error Codes
- `400`: Bad Request - Invalid input data
- `401`: Unauthorized - Authentication required
- `403`: Forbidden - Insufficient permissions
- `404`: Not Found - Resource not found
- `500`: Internal Server Error - Server-side error

## Rate Limiting
- 100 requests per minute per IP
- 1000 requests per hour per user

## Best Practices
1. Always include authentication token
2. Use appropriate HTTP methods
3. Handle errors gracefully
4. Implement retry logic for failed requests
5. Cache responses when appropriate