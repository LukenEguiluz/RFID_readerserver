# Integration bundle (RFID_readerserver)

Este directorio vive **dentro del repo** `RFID_readerserver`.

## Qué hace cada pieza

| Pieza | Rol |
|--------|-----|
| `docs-SOUL.md` | Visión del sistema (“túnel” RFID, eventos, sesiones, fallos esperados). Tras `COPIAR_AL_REPO.sh` queda en `docs/SOUL.md`. |
| `docker-compose.db.yml` | Solo PostgreSQL + red `rfid_tunnel_net` + volumen **nombrado** `rfid_tunnel_postgres_data`. |
| `docker-compose.app.yml` | Solo el gateway; red externa; host `rfidgateway-postgres`. Puerto **host** por defecto **38080** → 8080 en el contenedor (`GATEWAY_HTTP_PORT`). |
| `deploy.sh` | Orquesta DB y app: `up`, `update-app` (rebuild sin tocar datos), `stop-app`, `stop-all` **sin `-v`**. |
| `inventory-mode-patch/` | Parche inventario continuo / sistemas de inventario. |

## Instalar archivos de despliegue en la raíz del repo

Desde cualquier sitio:

```bash
bash integration-bundle/COPIAR_AL_REPO.sh
```

Por defecto copia a la carpeta **padre** de `integration-bundle/` (la raíz del repo). Otro destino: `bash COPIAR_AL_REPO.sh /ruta/otro/repo`.

## Uso en producción / actualizar solo la app

En la raíz del repo (tras copiar):

```bash
./deploy.sh up          # primera vez o stack completo
./deploy.sh update-app # tras `git pull` o cambios de código: rebuild gateway, DB intacta
```

**No** uses `docker compose down -v` en el stack de DB si quieres conservar datos; el volumen `rfid_tunnel_postgres_data` es el almacenamiento persistente.

El `docker-compose.yml` monolítico de la raíz sigue sirviendo para un solo `docker compose up` (desarrollo); usa el mismo nombre de volumen que el stack de DB para alinear persistencia.

- **`inventory-mode-patch/`** — Para repetir el volcado del parche sobre `src/`:
  ```bash
  bash integration-bundle/inventory-mode-patch/COPY_PATCH.sh "$(pwd)"
  ```
- Para **volver a copiar** el bundle desde tu home (si lo tienes ahí):  
  `bash ~/RFID_tunnel_integration_bundle/integrar-en-repo.sh`  
  (detecta `RFID_readerserver` o `RFID_tunnel` al lado del bundle).
