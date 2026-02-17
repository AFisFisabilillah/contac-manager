# Security & Error Scenarios

Dokumen ini merangkum kemungkinan kejadian (event) pada authorization dan schema respons yang dihasilkan API.

## Authorization Flow (Spring Security)

- Public endpoint:
  - `POST /api/auth/login`
  - `POST /api/users`
- Protected endpoint:
  - endpoint lain wajib menyertakan header `X-API-TOKEN` yang valid.

## Possible Events & Responses

### 1) Token tidak dikirim
- **Condition**: Header `X-API-TOKEN` tidak ada / kosong.
- **HTTP Status**: `401 Unauthorized`
- **Response Schema**:

```json
{
  "message": "Unauthorized",
  "errors": null
}
```

### 2) Token tidak valid atau tidak ditemukan
- **Condition**: token tidak match pada database.
- **HTTP Status**: `401 Unauthorized`
- **Response Schema**: sama seperti skenario 1.

### 3) Token expired
- **Condition**: `tokenExpiredAt <= now`.
- **HTTP Status**: `401 Unauthorized`
- **Response Schema**: sama seperti skenario 1.

### 4) Akses ditolak (forbidden)
- **Condition**: user terautentikasi tapi tidak memiliki izin (future role-based use case).
- **HTTP Status**: `403 Forbidden`
- **Response Schema**:

```json
{
  "message": "Forbidden",
  "errors": null
}
```

### 5) Validasi request gagal (constraint/service validation)
- **Condition**: payload tidak sesuai constraint.
- **HTTP Status**: `400 Bad Request`
- **Response Schema**:

```json
{
  "message": "Maaf ada error",
  "errors": {
    "field": "validation message"
  }
}
```

### 6) JSON malformed
- **Condition**: body JSON rusak / format tidak valid.
- **HTTP Status**: `400 Bad Request`
- **Response Schema**:

```json
{
  "message": "Malformed JSON request",
  "errors": null
}
```

### 7) Exception tidak tertangani
- **Condition**: runtime exception yang tidak ditangani handler spesifik.
- **HTTP Status**: `500 Internal Server Error`
- **Response Schema**:

```json
{
  "message": "Internal server error",
  "errors": null
}
```

## Unit Testing Coverage

- `TokenAuthenticationFilterTest`
  - valid token => authentication terisi
  - expired token => authentication kosong
  - public endpoint => repository token lookup tidak dipanggil
- `ErrorControllerUnitTest`
  - `ConstraintViolationException`
  - `MethodArgumentNotValidException`
  - `HttpMessageNotReadableException`
  - `ResponseStatusException`
  - generic `Exception`
