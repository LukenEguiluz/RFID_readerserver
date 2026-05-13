#!/usr/bin/env bash
# Copia docker-compose.db.yml, docker-compose.app.yml y deploy.sh desde la raíz del repo
# hacia otro directorio (p. ej. servidor de producción).
# Uso: ./scripts/instalar-en-proyecto.sh /opt/apps/RFID_readerserver
set -euo pipefail
DEST="${1:-/opt/apps/RFID_readerserver}"
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
for f in docker-compose.db.yml docker-compose.app.yml deploy.sh; do
  install -m 0644 "$ROOT/$f" "$DEST/$f"
done
chmod +x "$DEST/deploy.sh"
echo "Instalado en: $DEST"
echo "Desde ahí ejecuta: ./deploy.sh"
