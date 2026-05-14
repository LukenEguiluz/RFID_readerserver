# Inventario de pantallas (UI web) — RFID Gateway

Documento orientado a **rediseño visual** (otra IA o equipo de diseño). Describe cada vista HTML servida por el gateway, su **URL**, **plantilla Thymeleaf**, **controlador** aproximado y **contenido funcional** (no es guía de API REST completa).

## Convenciones técnicas

| Aspecto | Detalle |
|--------|---------|
| Motor de vistas | **Thymeleaf** (`src/main/resources/templates/*.html`) |
| Estilos | **CSS embebido** en cada plantilla (no hay hoja global única) |
| Identidad visual actual | Rojo `#dc2626` / `#991b1b` en dashboard; grises y blancos en formularios; acentos azules en grupos (`#4299e1`) |
| Iconografía | Uso frecuente de **emoji** en títulos y botones (ej. 📋, 🏷️) |
| Mensajes | Flash / modelo: `success`, `error` (según pantalla) |
| Híbridas | **`tags.html`** combina HTML + **JavaScript** (fetch, WebSocket, tablas dinámicas) |
| Errores HTTP | **`error.html`** vía `CustomErrorController` (Whitelabel desactivado en `application.yml`) |

---

## Resumen rápido

| # | Ruta(s) | Plantilla | Rol |
|---|---------|-----------|-----|
| 1 | `/` | `index.html` | Inicio / dashboard |
| 2 | `/readers` | `readers.html` | Lista de lectores **y** tabla de grupos (misma página) |
| 3 | `/readers/new` | `reader-form.html` | Alta de lector |
| 4 | `/readers/{id}/edit` | `reader-edit.html` | Edición de lector |
| 5 | `/readers/{id}/antennas` | `reader-antennas.html` | Antenas, potencias TX/RX, detección Impinj |
| 6 | `/tags` | `tags.html` | Vista en vivo de tags (JS + WS) |
| 7 | `/api-docs` | `api-docs.html` | Documentación API embebida en HTML |
| 8 | `/groups` | `groups.html` | Lista de grupos de lectores |
| 9 | `/groups/new`, `/groups/{id}/edit` | `group-form.html` | Crear / editar grupo |
| 10 | `/inventory-systems` | `inventory-systems.html` | Lista sistemas inventario continuo |
| 11 | `/inventory-systems/new`, `/inventory-systems/{id}/edit` | `inventory-system-form.html` | Crear / editar sistema |
| 12 | `/inventory-systems/{id}/epcs` | `inventory-system-epcs.html` | EPC presentes (poll JSON) |
| 13 | *(errores)* | `error.html` | Página de error genérica |

**No usada:** `antenna-form.html` está **vacía** y no hay ninguna ruta que la resuelva (candidata a borrar o a implementar).

---

## 1. Inicio — Dashboard

| Campo | Valor |
|-------|--------|
| **URL** | `GET /` |
| **Plantilla** | `index.html` |
| **Controlador** | `WebController.index` |
| **Modelo** | `totalReaders`, `connectedReaders`; opcional `success` / `error` |

**Contenido:** Título “doHealth RFID Gateway”, dos tarjetas de estadísticas (total lectores, conectados), bloque de enlaces: gestionar lectores, inventario continuo, grupos, tags, alta lector, documentación APIs, enlace externo a `/api/status` (JSON, no es pantalla HTML).

**Diseño:** Fondo con gradiente rojo; tarjetas blancas con sombra.

---

## 2. Lista de lectores

| Campo | Valor |
|-------|--------|
| **URL** | `GET /readers` |
| **Plantilla** | `readers.html` |
| **Controlador** | `WebController.readers` |
| **Modelo** | `readers`, `groups` |

**Contenido:** En la **misma página** hay (1) **tabla de lectores** con estado conexión/lectura, hostname, etc. y acciones por fila: enlace **Antenas**, **Editar**, POST **Conectar**, **Desconectar**, acciones tipo reset/reboot (según JS), **Eliminar**; (2) si hay grupos, **segunda tabla de grupos** con editar/eliminar y CTA “Crear primer grupo” si la lista está vacía. Cabecera con enlaces a inicio, tags, alta lector, grupos, APIs.

**Diseño:** Fondo gris claro; tablas blancas; botones rojos/verde/naranja.

---

## 3. Alta de lector

| Campo | Valor |
|-------|--------|
| **URL** | `GET /readers/new`, `POST /readers` |
| **Plantilla** | `reader-form.html` |
| **Controlador** | `WebController.readerForm`, `readerCreate` |
| **Modelo** | `reader` (form binding); `error` en validación |

**Campos:** ID, nombre, **marca** (`ReaderBrand`), hostname/IP, habilitado. Texto de ayuda: solo Impinj Octane conecta vía SDK.

---

## 4. Edición de lector

| Campo | Valor |
|-------|--------|
| **URL** | `GET /readers/{id}/edit`, `POST /readers/{id}/edit` |
| **Plantilla** | `reader-edit.html` |
| **Controlador** | `WebController.readerEdit`, `readerUpdate` |
| **Modelo** | `reader`, `inventorySystems` (lista para dropdown sistema inventario), `error` |

**Campos:** ID solo lectura; nombre; hostname; marca; **modo operación** (TUNNEL / CONTINUOUS); **sistema de inventario** (opcional); habilitado.

**Nota UX:** Las antenas se gestionan desde la lista de lectores (ruta `/readers/{id}/antennas`), no desde esta pantalla.

---

## 5. Antenas del lector

| Campo | Valor |
|-------|--------|
| **URL** | `GET /readers/{id}/antennas`; `POST` discover, alta/edición por puerto |
| **Plantilla** | `reader-antennas.html` |
| **Controlador** | `WebController.readerAntennas`, `readerAntennasDiscover`, `readerAntennaAdd` |
| **Modelo** | `reader`, `antennas`, `hardware` (opcional, Impinj conectado), `rfOptions` (listas TX/RX), `success` / `error` |

**Contenido:** Bloque hardware (modelo, firmware, número de antenas). Detección/sincronización Impinj. **Listados** de potencias TX y sensibilidades RX (`<details>`). **Preset** por tipo de instalación (JS que rellena selects). Formulario alta por puerto (nombre, TX, RX, estado). Tabla de antenas con **formulario por fila** (`display:contents`) para guardar nombre, activa/inactiva, TX, RX. Enlaces a lista lectores y edición lector.

**Diseño:** Ancho ~960px; tablas y cajas blancas.

---

## 6. Tags leídos (vista en vivo)

| Campo | Valor |
|-------|--------|
| **URL** | `GET /tags` |
| **Plantilla** | `tags.html` |
| **Controlador** | `WebController.tags` |
| **Modelo** | `readers` (para filtros / selector) |

**Contenido:** Interfaz **rica en JavaScript**: tablas dinámicas, RSSI, filtros, conexión **WebSocket** para eventos en tiempo real (según implementación en el propio archivo). Es la pantalla más compleja para rediseño + pruebas de regresión JS.

---

## 7. Documentación de APIs (HTML)

| Campo | Valor |
|-------|--------|
| **URL** | `GET /api-docs` |
| **Plantilla** | `api-docs.html` |
| **Controlador** | `WebController.apiDocs` |
| **Modelo** | `readers` (para ejemplos con IDs) |

**Contenido:** Secciones estáticas / semiestáticas describiendo endpoints REST, cuerpos JSON, ejemplos. Muy extensa; cambios de copy y jerarquía visual afectan poco al backend.

---

## 8. Lista de grupos

| Campo | Valor |
|-------|--------|
| **URL** | `GET /groups` |
| **Plantilla** | `groups.html` |
| **Controlador** | `WebController.groups` |
| **Modelo** | `groups`, `success` / `error` |

**Contenido:** Tabla de grupos (ID, nombre, descripción, número lectores, conectados, habilitado). Acciones: editar, eliminar. Enlaces a inicio, lectores, crear grupo, APIs.

**Diseño:** Cabecera de tabla azul (`#4299e1`), coherente con “módulo grupos”.

---

## 9. Crear / editar grupo

| Campo | Valor |
|-------|--------|
| **URL** | `GET/POST /groups/new`, `GET/POST /groups/{id}/edit` |
| **Plantilla** | `group-form.html` (una sola plantilla; dos modos) |
| **Controlador** | `WebController.groupForm`, `groupCreate`, `groupEdit`, `groupUpdate` |
| **Modelo** | `group`, `readers`, `error` |

**Contenido:** Creación: ID, nombre, descripción, multiselect de lectores, habilitado. Edición: mismos campos sin cambiar ID (readonly). Botón cancelar enlaza a `/readers` en alta (revisar coherencia navegación al rediseñar).

---

## 10. Inventario continuo — lista de sistemas

| Campo | Valor |
|-------|--------|
| **URL** | `GET /inventory-systems` |
| **Plantilla** | `inventory-systems.html` |
| **Controlador** | `InventorySystemWebController.list` |
| **Modelo** | `systems`, `success` / `error` |

**Contenido:** Tabla: ID, nombre, ciclo global (s), activo. Acciones: **EPCs** (vista en vivo), **Editar**, **Eliminar** (POST). Enlace “Nuevo sistema”. Pie con mención a APIs `/api/inventory-systems/...` y WebSocket.

---

## 11. Crear / editar sistema de inventario continuo

| Campo | Valor |
|-------|--------|
| **URL** | `GET /inventory-systems/new`, `GET /inventory-systems/{id}/edit`, `POST` create/edit |
| **Plantilla** | `inventory-system-form.html` |
| **Controlador** | `InventorySystemWebController` |
| **Modelo** | `system`, `members`, `allReaders`, `success` / `error` |

**Contenido:** ID (solo en alta), nombre, ciclo global (segundos), activado; tabla de **miembros** (lectores del sistema, orden, segundos por slot). Enlace “Ver EPC en vivo” si el sistema ya tiene ID. Volver a lista.

---

## 12. EPC en vivo (por sistema)

| Campo | Valor |
|-------|--------|
| **URL** | `GET /inventory-systems/{id}/epcs` |
| **Plantilla** | `inventory-system-epcs.html` |
| **Controlador** | `InventorySystemWebController.liveEpcs` |
| **Modelo** | `systemId`, `systemName` |

**Contenido:** Tabla rellenada por **JavaScript** con `fetch` cada ~2 s a `GET /api/inventory-systems/{id}/epcs/current`. Enlaces a lista sistemas y edición del sistema.

---

## 13. Página de error

| Campo | Valor |
|-------|--------|
| **URL** | Cualquier error que resuelva `CustomErrorController` (ej. 4xx/5xx con vista de error) |
| **Plantilla** | `error.html` |
| **Modelo** | `status`, `error`, `message` (según disponibilidad) |

**Contenido:** Mensaje breve, enlaces a inicio, `/api/status`, `/api/health`.

---

## Rutas útiles que no son “pantalla Thymeleaf”

| Ruta | Tipo |
|------|------|
| `/api/status`, `/api/health`, resto de `/api/**` | JSON / REST |
| `/ws/**` (si está expuesto) | WebSocket |

Convendrá que el rediseño mantenga **contratos** de estas rutas si la nueva UI sigue consumiéndolas desde el navegador.

---

## Duplicados fuera de `src/`

En `integration-bundle/inventory-mode-patch/` hay copias parciales de algunas plantillas (`index`, `inventory-system-*`, `reader-edit`). La **fuente de verdad** para el producto es **`src/main/resources/templates/`**.

---

## Checklist sugerido para la IA de diseño

1. Unificar **tokens** (color, radio, tipografía, espaciado) y decidir si se mantienen emojis.
2. Extraer CSS común (hoy duplicado en ~12 archivos).
3. Revisar **accesibilidad** (contraste, foco, labels en formularios con `display:contents`).
4. Probar **`tags.html`** y **`inventory-system-epcs.html`** tras cambios (JS).
5. Confirmar navegación entre **lectores ↔ antenas ↔ inventario continuo ↔ EPCs**.
6. Eliminar o implementar **`antenna-form.html`**.

---

*Última revisión según estructura del repositorio RFID_readerserver (plantillas bajo `src/main/resources/templates`).*
