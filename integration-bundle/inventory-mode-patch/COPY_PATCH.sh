#!/usr/bin/env bash
# Fusiona el parche "inventario continuo" en el árbol del repo (RFID_readerserver o ruta explícita).
# Uso: ./COPY_PATCH.sh /home/dohealth/RFID_readerserver
set -euo pipefail
TARGET="${1:-/home/dohealth/RFID_readerserver}"
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
if [[ ! -d "$TARGET/src/main/java" ]]; then
  echo "Destino no parece el repo: $TARGET" >&2
  exit 1
fi
cp -a "$ROOT/src/main/java/." "$TARGET/src/main/java/"
cp -a "$ROOT/src/main/resources/." "$TARGET/src/main/resources/"
echo "Parche copiado en: $TARGET"
