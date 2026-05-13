# Parche: inventario continuo + API lectura + UI

Copiar al repo y compilar:

```bash
bash /home/dohealth/RFID_tunnel_integration_bundle/inventory-mode-patch/COPY_PATCH.sh /home/dohealth/RFID_tunnel
cd /home/dohealth/RFID_tunnel && mvn -q -DskipTests package
```

## Contenido

- Modo lector `TUNNEL` | `CONTINUOUS`, FK lógica `inventory_system_id`.
- Entidades: `InventorySystem`, `InventorySystemReader`, `InventorySystemEpcState`, `EpcPresenceEvent`.
- Orquestador por ciclo: tiempo por lector repartido entre antenas habilitadas; ciclo global en segundos.
- EPC únicos **por sistema**; eventos ADD al primer avistamiento en BD; REMOVE al cerrar ciclo si no se vio el EPC estando presente.
- REST **solo lectura**: `/api/inventory-systems`, `.../epcs/current`, `.../events`, `.../epcs/{epc}/timeline`.
- WebSocket: `INVENTORY_CYCLE_START`, `INVENTORY_EPC_ADD`, `INVENTORY_EPC_REMOVE`.
- UI: `/inventory-systems`, formulario crear/editar; edición lector con modo y sistema.
- Bloqueo túnel: `POST /api/readers/{id}/start|stop` y sesiones devuelven 409 en modo CONTINUOUS.

## Notas

- Hibernate `ddl-auto: update` creará tablas/columnas al arrancar.
- Si el repo es de `root`, ejecuta `COPY_PATCH.sh` con `sudo`.
