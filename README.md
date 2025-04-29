# 🚀 Instrucciones para levantar el proyecto de prueba

## 📦 Crear la base de datos

```bash
docker-compose up -d
```

## ⚙️ Correr el programa

```bash
./mvnw spring-boot:run
```

> **Nota:** El proyecto utiliza **Lombok**.

## 🧪 Ejecutar los tests

```bash
./mvnw test
```

---

# 📚 Servicios implementados

## 🧍‍♂️ Cliente

### 🔹 Buscar clientes por criterio

- **Método:** `GET`
- **URL:** `http://localhost:8080/api/client/searchClientsByCriteria`
- **Body de ejemplo:**

```json
{
  "id": null,
  "criteria": "11022382343"
}
```

---

### 🔹 Crear cliente

- **Método:** `POST`
- **URL:** `http://localhost:8080/api/client/createClient`
- **Body de ejemplo:**

```json
{
  "firstName": "Denisse",
  "lastName": "Galarza",
  "identificationNumber": "11022382342",
  "cellPhone": "0987654321",
  "email": "denisse@mail.com",
  "identificationType": 1,
  "province": "Loja",
  "city": "Loja",
  "address": "Miraflores"
}
```

---

### 🔹 Editar cliente

- **Método:** `PUT`
- **URL:** `http://localhost:8080/api/client/editClient/{id}`
- **Body de ejemplo:**

```json
{
  "id": 1,
  "firstName": "Denisse 111",
  "lastName": "Galarza 111",
  "identificationNumber": "1105669657",
  "cellPhone": "0987654321",
  "email": "denisse@mail.com",
  "identificationType": {
    "id": 1
  }
}
```

---

### 🔹 Eliminar cliente

- **Método:** `DELETE`
- **URL:** `http://localhost:8080/api/client/deleteClient/{id}`

---

## 🏠 Dirección (Address)

### 🔹 Obtener todas las direcciones de un cliente

- **Método:** `GET`
- **URL:** `http://localhost:8080/api/address/getAllAddressByClient`
- **Body de ejemplo:**

```json
{
  "id": 1,
  "criteria": null
}
```

---

### 🔹 Agregar dirección a un cliente

- **Método:** `POST`
- **URL:** `http://localhost:8080/api/address/addAddress`
- **Body de ejemplo:**

```json
{
  "address": {
    "province": "Loja",
    "city": "Loja",
    "address": "La Banda",
    "isMainAddress": true
  },
  "clientId": 1
}
```

---
