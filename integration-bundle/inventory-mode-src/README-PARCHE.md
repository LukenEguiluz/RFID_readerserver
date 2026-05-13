# Modo inventario continuo (parche)

Copiar al repo del gateway (requiere permisos sobre `RFID_tunnel`):

```bash
sudo rsync -a --relative \
  /home/dohealth/RFID_tunnel_integration_bundle/inventory-mode-src/./home/dohealth/RFID_tunnel/ \
  /
```

O, si prefieres copiar solo dentro del proyecto:

```bash
sudo cp -r /home/dohealth/RFID_tunnel_integration_bundle/inventory-mode-src/home/dohealth/RFID_tunnel/* \
  /home/dohealth/RFID_tunnel/
```

Luego `mvn -q -DskipTests package` desde el repo.

## Resumen funcional

- **Modo por lector**: `TUNNEL` (actual) o `CONTINUOUS` (inventario cíclico). Se cambia solo por **UI** (formulario lector + sistema).
- **Sistema** (`inventory_systems`): unidad con ciclo global en segundos; lectores miembros con **orden** y **segundos totales por lector** por ciclo; el tiempo se reparte **equitativamente entre antenas habilitadas** de ese lector.
- **EPC únicos por sistema** (universo por interfaz/sistema): estado actual + historial de eventos `ADD` / `REMOVE` al cerrar ciclo si el EPC no se vio en ese ciclo estando marcado presente.
- **API REST solo lectura**: `/api/inventory-systems/...`
- **WebSocket**: tipos `INVENTORY_EPC_ADD`, `INVENTORY_EPC_REMOVE`, `INVENTORY_CYCLE_START`.
- **Túnel**: `POST /api/readers/.../start` y sesiones devuelven 409 si el lector está en modo continuo.

Los archivos bajo `home/dohealth/RFID_tunnel/` replican rutas relativas al repo.
