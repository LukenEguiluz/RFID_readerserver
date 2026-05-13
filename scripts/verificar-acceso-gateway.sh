#!/usr/bin/env bash
# Ejecutar EN LA MÁQUINA DONDE CORRE DOCKER. Puerto publicado en el host (ver docker-compose).
set -euo pipefail
PORT="${GATEWAY_HTTP_PORT:-38080}"
echo "=== ¿Quién escucha en :${PORT}? (debe aparecer docker-proxy) ==="
ss -tlnp 2>/dev/null | grep ":${PORT} " || true

echo ""
echo "=== Health vía localhost (host → contenedor) ==="
if curl -sS -m 5 -f "http://127.0.0.1:${PORT}/api/health" >/dev/null; then
  echo "OK: curl http://127.0.0.1:${PORT}/api/health"
else
  echo "FALLO: el gateway no responde en 127.0.0.1:${PORT}"
fi

echo ""
echo "=== IPs del servidor (usa la de la VPN/tun/wg0 en el OTRO equipo) ==="
ip -4 -o addr show scope global 2>/dev/null | awk '{print $2, $4}' | sed 's|/.*||' || hostname -I 2>/dev/null || true

echo ""
echo "=== Firewall ufw (si está activo, hace falta allow ${PORT}/tcp) ==="
if command -v ufw >/dev/null 2>&1; then
  sudo ufw status 2>/dev/null || ufw status 2>/dev/null || echo "(sin permiso para ver ufw)"
else
  echo "ufw no instalado"
fi

echo ""
echo "Recuerda en el navegador: http://<IP_VPN_DEL_SERVIDOR>:${PORT}/  (http, no https)"
