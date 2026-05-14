# Cómo pegar tu diseño para adaptar solo el estilo

Usa este documento cuando quieras que adapte la UI **sin cambiar funcionalidad** (mismas rutas, formularios, `name` de inputs, Thymeleaf `th:*`, JavaScript que llama APIs, etc.). Solo se tocarán **HTML de presentación** y **CSS** (y, si aplica, clases o estructura mínima de contenedores que no rompan los bindings).

---

## Dónde pegar el código de diseño

**Pégalo aquí abajo** (en este mismo archivo, en la sección [Tu diseño](#tu-diseño-más-abajo)), o en un mensaje del chat **después** de esta plantilla, en este orden:

1. **Tokens globales** (si los tienes): colores, tipografía, radios, sombras, espaciado (CSS variables, JSON de tokens, o fragmento de tema).
2. **Estilos base / layout**: contenedor principal, header, cards, tablas, botones, inputs, alerts.
3. **Por pantalla** (opcional pero ayuda): solo las que quieras rediseñar primero; indica nombre del archivo del proyecto si lo sabes (ej. `readers.html`).

Puedes pegar:

- CSS puro o SCSS.
- HTML de referencia (maquetas) **sin** necesidad de que sea Thymeleaf; yo lo mapeo a las plantillas existentes.
- Enlaces a Figma / capturas: describe componentes clave o pega export de CSS si Figma lo genera.
- Librería (ej. solo variables de Bootstrap / Tailwind config): indica si **no** quieres añadir dependencias al `pom.xml` (por defecto **no** añado frameworks nuevos; solo CSS en plantillas o un `static/css/` si lo acordamos).

---

## Lo que no debes incluir (para no confundir alcance)

- No hace falta pegar el código Java actual.
- Si pegas HTML de maqueta, **no** reemplaces sola la plantilla entera sin revisión: los `th:field`, `th:action`, `th:each` y los `name` de los formularios deben conservarse.

---

## Compromiso de adaptación

| Se mantiene | Se puede cambiar |
|-------------|------------------|
| URLs, métodos POST/GET, nombres de parámetros de formularios | Colores, fuentes, márgenes, bordes, sombras |
| `th:*` de Thymeleaf y lógica de atributos | Clases CSS, orden visual de bloques si los formularios siguen enviando lo mismo |
| IDs usados por JS (si los hay) — avisar cuáles son | Etiquetas visuales, iconos, textos de ayuda *cosméticos* |
| Scripts que llaman a `/api/...` o WebSocket | Estilo de tablas/listas generadas por JS (clases, contenedores) |

Si tu diseño **exige** renombrar IDs o cambiar contratos de API, dímelo explícitamente; eso ya no es “solo estilo”.

---

## Archivos que suelen tocarse (referencia)

Plantillas bajo `src/main/resources/templates/`:

`index.html`, `readers.html`, `reader-form.html`, `reader-edit.html`, `reader-antennas.html`, `tags.html`, `api-docs.html`, `groups.html`, `group-form.html`, `inventory-systems.html`, `inventory-system-form.html`, `inventory-system-epcs.html`, `error.html`.

Hoy casi todo el CSS va **inline** en cada archivo; el diseño puede centralizarse en `src/main/resources/static/css/theme.css` (o el nombre que indiques) y enlazarse desde las plantillas **sin** cambiar la lógica.

---

## Tu diseño (pegar debajo)

<!-- PEGA AQUÍ TU CÓDIGO: CSS, HTML de referencia, tokens, etc. -->

**Notas opcionales** (rellena si aplica):

- Prioridad de pantallas: …
- Oscuro / claro / ambos: …
- ¿Permitir nueva dependencia (Bootstrap, Tailwind build, etc.)? Sí / No  
- Breakpoints móvil importantes: …

---

*Cuando este bloque esté relleno, en un mensaje pide: “adapta el estilo según `docs/DISENO_PARA_ADAPTAR.md`” y se trabajará solo sobre presentación.*


<!-- Design System -->
<!DOCTYPE html>

<html class="light" lang="en"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<title>doHealth RFID Gateway - Dashboard</title>
<script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&amp;display=swap" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<script id="tailwind-config">
        tailwind.config = {
            darkMode: "class",
            theme: {
                extend: {
                    "colors": {
                        "surface-container": "#eeeeed",
                        "on-primary-fixed": "#410005",
                        "secondary-fixed-dim": "#c3c7c8",
                        "tertiary-fixed-dim": "#ffb3b1",
                        "primary-container": "#e0132c",
                        "surface-container-lowest": "#ffffff",
                        "on-tertiary": "#ffffff",
                        "outline-variant": "#e7bdb9",
                        "surface-container-low": "#f4f3f3",
                        "on-tertiary-container": "#fff4f3",
                        "secondary-fixed": "#dfe3e4",
                        "on-error-container": "#93000a",
                        "on-primary-container": "#fff4f3",
                        "secondary": "#5a5f60",
                        "on-secondary-fixed": "#171c1d",
                        "error": "#ba1a1a",
                        "on-background": "#1a1c1c",
                        "surface-tint": "#c00021",
                        "error-container": "#ffdad6",
                        "background": "#faf9f9",
                        "surface": "#faf9f9",
                        "tertiary-fixed": "#ffdad8",
                        "primary-fixed": "#ffdad7",
                        "on-surface-variant": "#5d3f3d",
                        "surface-bright": "#faf9f9",
                        "on-tertiary-fixed": "#410007",
                        "surface-container-highest": "#e3e2e2",
                        "surface-dim": "#dadada",
                        "on-error": "#ffffff",
                        "surface-variant": "#e3e2e2",
                        "on-secondary-container": "#5e6364",
                        "on-secondary": "#ffffff",
                        "primary": "#b5001e",
                        "tertiary": "#973a3b",
                        "surface-container-high": "#e8e8e8",
                        "on-primary-fixed-variant": "#930016",
                        "outline": "#926e6c",
                        "on-secondary-fixed-variant": "#434849",
                        "inverse-on-surface": "#f1f0f0",
                        "on-tertiary-fixed-variant": "#7f282b",
                        "inverse-primary": "#ffb3af",
                        "tertiary-container": "#b65151",
                        "on-primary": "#ffffff",
                        "primary-fixed-dim": "#ffb3af",
                        "secondary-container": "#dce0e1",
                        "on-surface": "#1a1c1c",
                        "inverse-surface": "#2f3131"
                    },
                    "borderRadius": {
                        "DEFAULT": "0.125rem",
                        "lg": "0.25rem",
                        "xl": "0.5rem",
                        "full": "0.75rem"
                    },
                    "spacing": {
                        "max-width": "1440px",
                        "margin-mobile": "16px",
                        "gutter": "16px",
                        "xs": "4px",
                        "xl": "32px",
                        "lg": "24px",
                        "md": "16px",
                        "base": "4px",
                        "margin-desktop": "32px",
                        "sm": "8px"
                    },
                    "fontFamily": {
                        "title-md": ["Inter"],
                        "label-md": ["Inter"],
                        "body-lg": ["Inter"],
                        "headline-lg-mobile": ["Inter"],
                        "body-md": ["Inter"],
                        "code-md": ["monospace"],
                        "headline-md": ["Inter"],
                        "display-lg": ["Inter"],
                        "headline-lg": ["Inter"]
                    },
                    "fontSize": {
                        "title-md": ["18px", {"lineHeight": "24px", "fontWeight": "600"}],
                        "label-md": ["12px", {"lineHeight": "16px", "letterSpacing": "0.05em", "fontWeight": "600"}],
                        "body-lg": ["16px", {"lineHeight": "24px", "fontWeight": "400"}],
                        "headline-lg-mobile": ["24px", {"lineHeight": "32px", "fontWeight": "600"}],
                        "body-md": ["14px", {"lineHeight": "20px", "fontWeight": "400"}],
                        "code-md": ["13px", {"lineHeight": "18px", "fontWeight": "400"}],
                        "headline-md": ["24px", {"lineHeight": "32px", "fontWeight": "600"}],
                        "display-lg": ["48px", {"lineHeight": "56px", "letterSpacing": "-0.02em", "fontWeight": "700"}],
                        "headline-lg": ["32px", {"lineHeight": "40px", "letterSpacing": "-0.01em", "fontWeight": "600"}]
                    }
                },
            },
        }
    </script>
<style>
        .material-symbols-outlined {
            font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
            vertical-align: middle;
        }
        body { font-family: 'Inter', sans-serif; }
    </style>
</head>
<body class="bg-background text-on-background min-h-screen">
<!-- Sidebar Navigation -->
<aside class="fixed left-0 top-0 w-[260px] h-full bg-on-secondary-fixed-variant dark:bg-inverse-surface border-r border-outline-variant dark:border-outline flex flex-col py-xl px-md gap-sm z-50">
<div class="mb-xl px-4">
<h1 class="font-title-md text-title-md text-surface-bright font-bold">doHealth</h1>
<p class="text-secondary-fixed-dim text-[10px] uppercase tracking-widest">RFID Control Center</p>
</div>
<nav class="flex flex-col gap-xs">
<a class="bg-primary-container text-on-primary-container font-bold rounded-lg px-4 py-3 flex items-center gap-md Active: translate-x-1 duration-200" href="#">
<span class="material-symbols-outlined" data-icon="dashboard">dashboard</span>
<span class="font-label-md text-label-md">Dashboard</span>
</a>
<a class="text-secondary-fixed-dim dark:text-secondary-fixed hover:bg-on-secondary-fixed hover:text-surface-bright px-4 py-3 flex items-center gap-md transition-all" href="#">
<span class="material-symbols-outlined" data-icon="sensors">sensors</span>
<span class="font-label-md text-label-md">Readers</span>
</a>
<a class="text-secondary-fixed-dim dark:text-secondary-fixed hover:bg-on-secondary-fixed hover:text-surface-bright px-4 py-3 flex items-center gap-md transition-all" href="#">
<span class="material-symbols-outlined" data-icon="label">label</span>
<span class="font-label-md text-label-md">Live Tags</span>
</a>
<a class="text-secondary-fixed-dim dark:text-secondary-fixed hover:bg-on-secondary-fixed hover:text-surface-bright px-4 py-3 flex items-center gap-md transition-all" href="#">
<span class="material-symbols-outlined" data-icon="group">group</span>
<span class="font-label-md text-label-md">Groups</span>
</a>
<a class="text-secondary-fixed-dim dark:text-secondary-fixed hover:bg-on-secondary-fixed hover:text-surface-bright px-4 py-3 flex items-center gap-md transition-all" href="#">
<span class="material-symbols-outlined" data-icon="inventory_2">inventory_2</span>
<span class="font-label-md text-label-md">Inventory Systems</span>
</a>
</nav>
<div class="mt-auto pt-md border-t border-secondary">
<div class="flex items-center gap-md px-4">
<div class="w-8 h-8 rounded-full bg-secondary-container flex items-center justify-center overflow-hidden">
<img alt="Admin" data-alt="A professional portrait of a system administrator in a high-tech medical facility environment. The lighting is bright and surgical, emphasizing a clean and authoritative atmosphere. The color palette is dominated by neutral whites and grays with subtle tech-inspired accents. Professional and focused expression." src="https://lh3.googleusercontent.com/aida-public/AB6AXuBUzrgX_mn_rGTABAkCQwAN72ZGlBzq_3tevbk5fnWfmX-7pl6o7hU24ssFtFu1Q7ANuwUluuq62ZBebdbGS0p4bQ21a1VraOB6vhaU0rYSfRh-lwkc3KAjPrNeBrod5zT-va5CWh18SdqXEPRYZNFHpdL7MbyBfIYMXTFd-RdWP-jlBXexMW4hCqHzPWAKc_ydBDhSfiwLmyqggqUyK0hiUjdyfjlwMXX6qeQj0SLh9Y7HkeGsIb1OYH5Yil94uPikyRtw5mEm4Zo"/>
</div>
<div>
<p class="text-surface-bright font-label-md text-label-md">System Administrator</p>
<p class="text-secondary-fixed-dim text-[10px]">Level 4 Access</p>
</div>
</div>
</div>
</aside>
<!-- Main Content Stage -->
<main class="ml-[260px] min-h-screen">
<!-- Top App Bar -->
<header class="bg-surface dark:bg-surface-dim border-b border-outline-variant dark:border-outline flex justify-between items-center w-full px-margin-desktop h-16 sticky top-0 z-40">
<div class="flex items-center gap-xl">
<span class="font-headline-md text-headline-md font-bold text-primary dark:text-primary-container">doHealth RFID Gateway</span>
</div>
<div class="flex items-center gap-md">
<div class="flex items-center gap-sm bg-surface-container px-md py-xs rounded-full">
<div class="w-2 h-2 rounded-full bg-[#10b981] shadow-[0_0_8px_#10b981]"></div>
<span class="text-on-surface font-label-md text-label-md">Connected</span>
</div>
<div class="flex gap-xs">
<button class="p-2 text-primary dark:text-primary-fixed-dim hover:bg-surface-container-low transition-colors rounded-full">
<span class="material-symbols-outlined" data-icon="health_and_safety">health_and_safety</span>
</button>
<button class="p-2 text-primary dark:text-primary-fixed-dim hover:bg-surface-container-low transition-colors rounded-full relative">
<span class="material-symbols-outlined" data-icon="notifications">notifications</span>
<span class="absolute top-2 right-2 w-2 h-2 bg-primary-container rounded-full border-2 border-surface"></span>
</button>
<button class="p-2 text-primary dark:text-primary-fixed-dim hover:bg-surface-container-low transition-colors rounded-full">
<span class="material-symbols-outlined" data-icon="account_circle">account_circle</span>
</button>
</div>
</div>
</header>
<!-- Dashboard Content -->
<div class="p-margin-desktop space-y-lg max-w-[1440px]">
<!-- Key Stats: Bento Grid Style -->
<div class="grid grid-cols-12 gap-gutter">
<div class="col-span-12 lg:col-span-4 bg-surface-container-lowest border border-outline-variant p-lg rounded-xl shadow-sm flex flex-col justify-between">
<div class="flex justify-between items-start">
<span class="text-on-surface-variant font-label-md text-label-md">Total Readers</span>
<span class="material-symbols-outlined text-primary" data-icon="sensors">sensors</span>
</div>
<div class="mt-md">
<p class="font-display-lg text-display-lg text-on-surface">24</p>
<p class="text-secondary font-label-md text-label-md flex items-center gap-xs">
<span class="material-symbols-outlined text-[14px] text-primary" data-icon="trending_up">trending_up</span>
                            +2 from last week
                        </p>
</div>
</div>
<div class="col-span-12 lg:col-span-4 bg-surface-container-lowest border border-outline-variant p-lg rounded-xl shadow-sm flex flex-col justify-between">
<div class="flex justify-between items-start">
<span class="text-on-surface-variant font-label-md text-label-md">Connected Readers</span>
<span class="material-symbols-outlined text-[#10b981]" data-icon="wifi_tethering">wifi_tethering</span>
</div>
<div class="mt-md">
<p class="font-display-lg text-display-lg text-on-surface">18</p>
<div class="w-full bg-surface-container h-1 rounded-full mt-sm">
<div class="bg-[#10b981] h-full rounded-full" style="width: 75%"></div>
</div>
<p class="text-secondary font-label-md text-label-md mt-xs">75% active connection rate</p>
</div>
</div>
<div class="col-span-12 lg:col-span-4 bg-primary text-on-primary p-lg rounded-xl shadow-sm flex flex-col justify-between overflow-hidden relative">
<div class="relative z-10">
<div class="flex justify-between items-start">
<span class="font-label-md text-label-md opacity-90">Uptime</span>
<span class="material-symbols-outlined" data-icon="query_stats">query_stats</span>
</div>
<div class="mt-md">
<p class="font-display-lg text-display-lg">99.8%</p>
<p class="font-label-md text-label-md opacity-80 uppercase tracking-wider">System Critical Limit: 99.5%</p>
</div>
</div>
<div class="absolute -right-4 -bottom-4 opacity-10">
<span class="material-symbols-outlined text-[120px]" data-icon="pulse">Pulse</span>
</div>
</div>
</div>
<!-- Quick Navigation Shortcuts -->
<div class="grid grid-cols-4 gap-gutter">
<button class="flex flex-col items-center justify-center gap-sm bg-surface-container-lowest border border-outline-variant p-lg rounded-xl hover:bg-surface-container-low transition-all group">
<div class="w-12 h-12 rounded-lg bg-primary-container text-on-primary-container flex items-center justify-center group-hover:scale-110 transition-transform">
<span class="material-symbols-outlined text-[28px]" data-icon="settings_remote">settings_remote</span>
</div>
<span class="font-title-md text-title-md text-on-surface">Manage Readers</span>
</button>
<button class="flex flex-col items-center justify-center gap-sm bg-surface-container-lowest border border-outline-variant p-lg rounded-xl hover:bg-surface-container-low transition-all group">
<div class="w-12 h-12 rounded-lg bg-secondary-container text-on-secondary-container flex items-center justify-center group-hover:scale-110 transition-transform">
<span class="material-symbols-outlined text-[28px]" data-icon="dynamic_feed">dynamic_feed</span>
</div>
<span class="font-title-md text-title-md text-on-surface">Continuous Inventory</span>
</button>
<button class="flex flex-col items-center justify-center gap-sm bg-surface-container-lowest border border-outline-variant p-lg rounded-xl hover:bg-surface-container-low transition-all group">
<div class="w-12 h-12 rounded-lg bg-secondary-container text-on-secondary-container flex items-center justify-center group-hover:scale-110 transition-transform">
<span class="material-symbols-outlined text-[28px]" data-icon="account_tree">account_tree</span>
</div>
<span class="font-title-md text-title-md text-on-surface">Groups</span>
</button>
<button class="flex flex-col items-center justify-center gap-sm bg-surface-container-lowest border border-outline-variant p-lg rounded-xl hover:bg-surface-container-low transition-all group">
<div class="w-12 h-12 rounded-lg bg-secondary-container text-on-secondary-container flex items-center justify-center group-hover:scale-110 transition-transform">
<span class="material-symbols-outlined text-[28px]" data-icon="sensors_kronecker">DesktopTower</span>
</div>
<span class="font-title-md text-title-md text-on-surface">Live Tags</span>
</button>
</div>
<!-- Layout: System Health & Active Events -->
<div class="grid grid-cols-12 gap-gutter">
<!-- System Health Card -->
<div class="col-span-12 lg:col-span-7 bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden flex flex-col h-[400px]">
<div class="p-lg border-b border-outline-variant flex justify-between items-center bg-[#363b3c]">
<div class="flex items-center gap-md">
<span class="material-symbols-outlined text-surface-bright" data-icon="health_metrics">health_metrics</span>
<h2 class="font-title-md text-title-md text-surface-bright">System Health Overview</h2>
</div>
<span class="px-md py-xs rounded-full bg-[#10b98120] text-[#10b981] font-label-md text-label-md border border-[#10b981]">Operational</span>
</div>
<div class="flex-1 overflow-y-auto p-0">
<table class="w-full text-left border-collapse">
<thead class="sticky top-0 bg-surface-container-low z-10 border-b border-outline-variant">
<tr>
<th class="p-md font-label-md text-label-md text-on-surface-variant uppercase">Time</th>
<th class="p-md font-label-md text-label-md text-on-surface-variant uppercase">Module</th>
<th class="p-md font-label-md text-label-md text-on-surface-variant uppercase">Event</th>
<th class="p-md font-label-md text-label-md text-on-surface-variant uppercase text-right">Status</th>
</tr>
</thead>
<tbody class="divide-y divide-outline-variant">
<tr class="hover:bg-surface-container-low transition-colors">
<td class="p-md font-code-md text-code-md text-secondary">14:24:02</td>
<td class="p-md font-body-md text-body-md font-bold">Reader_South_A1</td>
<td class="p-md font-body-md text-body-md text-on-surface">RFID Tag Bulk Scan Complete (42 tags)</td>
<td class="p-md text-right"><span class="inline-flex w-2 h-2 rounded-full bg-[#10b981]"></span></td>
</tr>
<tr class="hover:bg-surface-container-low transition-colors">
<td class="p-md font-code-md text-code-md text-secondary">14:22:15</td>
<td class="p-md font-body-md text-body-md font-bold">Main_Gateway</td>
<td class="p-md font-body-md text-body-md text-on-surface">Auto-sync with Cloud Database</td>
<td class="p-md text-right"><span class="inline-flex w-2 h-2 rounded-full bg-[#10b981]"></span></td>
</tr>
<tr class="hover:bg-surface-container-low transition-colors">
<td class="p-md font-code-md text-code-md text-secondary">14:18:50</td>
<td class="p-md font-body-md text-body-md font-bold">Reader_East_B4</td>
<td class="p-md font-body-md text-body-md text-on-surface">Signal Interference Detected</td>
<td class="p-md text-right"><span class="inline-flex w-2 h-2 rounded-full bg-primary-container"></span></td>
</tr>
<tr class="hover:bg-surface-container-low transition-colors">
<td class="p-md font-code-md text-code-md text-secondary">14:05:11</td>
<td class="p-md font-body-md text-body-md font-bold">Auth_Service</td>
<td class="p-md font-body-md text-body-md text-on-surface">User Admin Login Successful</td>
<td class="p-md text-right"><span class="inline-flex w-2 h-2 rounded-full bg-[#10b981]"></span></td>
</tr>
<tr class="hover:bg-surface-container-low transition-colors">
<td class="p-md font-code-md text-code-md text-secondary">13:58:22</td>
<td class="p-md font-body-md text-body-md font-bold">Reader_West_C2</td>
<td class="p-md font-body-md text-body-md text-on-surface">Re-connected after firmware patch</td>
<td class="p-md text-right"><span class="inline-flex w-2 h-2 rounded-full bg-[#10b981]"></span></td>
</tr>
</tbody>
</table>
</div>
<div class="p-md bg-surface-container-low border-t border-outline-variant text-center">
<button class="text-primary font-label-md text-label-md hover:underline">View Full System Logs</button>
</div>
</div>
<!-- Asset Visibility Card -->
<div class="col-span-12 lg:col-span-5 bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden relative group h-[400px]">
<div class="absolute inset-0 bg-cover bg-center" data-alt="A futuristic medical storage room with automated shelves and RFID scanners glowing with soft red and white light. The atmosphere is sterile, technical, and precise, mirroring a modern industrial medical facility. High-end lighting creates sharp shadows and professional depth." style="background-image: url('https://lh3.googleusercontent.com/aida-public/AB6AXuCbzyO9ehgVyhobV192Ok6-8wlE9H1ipNtjHjJVJ0AOlGbF2yxZ7tcNVhg58h7z3ZsHXltr1IHRBLQPOxWI6Wp1wuzCn2pkJmHbfjIW3mBKrnjLyXELgkMk6a1bJ1TLbchXZy3x_FLhQ_8X1KhSkTaXo9ISSEXdHjlLwCu26c47Ph-raAm21S6NNiH9hfgCamY2_dXXXHYxVWcRewsfwUDa_PFhY9QpwGLx9Kha-zKApBimmqFZR-LRVfaW0cPUGrtNjJx84M4-sTE')">
<div class="absolute inset-0 bg-gradient-to-t from-[#1a1c1c] via-transparent to-transparent opacity-80"></div>
</div>
<div class="absolute bottom-0 left-0 p-lg w-full">
<div class="flex items-center gap-sm mb-xs">
<span class="material-symbols-outlined text-primary-container" data-icon="location_searching">location_searching</span>
<span class="text-surface-bright font-label-md text-label-md uppercase tracking-widest">Facility Map - Zone A</span>
</div>
<h3 class="font-headline-md text-headline-md text-surface-bright mb-md">Real-time Tag Tracking</h3>
<div class="grid grid-cols-2 gap-md">
<div class="bg-[#ffffff15] backdrop-blur-md p-md rounded-lg border border-[#ffffff20]">
<p class="text-surface-bright opacity-70 text-[10px] uppercase">Active Tags</p>
<p class="text-surface-bright font-title-md text-title-md">1,240</p>
</div>
<div class="bg-[#ffffff15] backdrop-blur-md p-md rounded-lg border border-[#ffffff20]">
<p class="text-surface-bright opacity-70 text-[10px] uppercase">Moving</p>
<p class="text-surface-bright font-title-md text-title-md">84</p>
</div>
</div>
<button class="w-full mt-lg bg-primary-container text-on-primary-container font-label-md text-label-md py-md rounded-lg font-bold shadow-lg">Open Interactive Map</button>
</div>
</div>
</div>
</div>
</main>
<!-- Floating Action Button Contextual (For Dashboard) -->
<button class="fixed bottom-xl right-margin-desktop w-14 h-14 bg-primary-container text-on-primary-container rounded-full flex items-center justify-center shadow-xl hover:scale-105 active:scale-95 transition-all z-50">
<span class="material-symbols-outlined text-[32px]" data-icon="add">add</span>
</button>
</body></html>

<!-- Dashboard -->
<!DOCTYPE html>

<html class="light" lang="en"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&amp;family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<style>
        .material-symbols-outlined {
            font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
        }
        body {
            font-family: 'Inter', sans-serif;
        }
        /* Custom scrollbar for data density */
        .custom-scrollbar::-webkit-scrollbar {
            width: 4px;
        }
        .custom-scrollbar::-webkit-scrollbar-track {
            background: #2f3131;
        }
        .custom-scrollbar::-webkit-scrollbar-thumb {
            background: #626363;
            border-radius: 2px;
        }
    </style>
<script id="tailwind-config">
        tailwind.config = {
          darkMode: "class",
          theme: {
            extend: {
              "colors": {
                      "surface-container": "#eeeeed",
                      "on-primary-fixed": "#410005",
                      "secondary-fixed-dim": "#c3c7c8",
                      "tertiary-fixed-dim": "#ffb3b1",
                      "primary-container": "#e0132c",
                      "surface-container-lowest": "#ffffff",
                      "on-tertiary": "#ffffff",
                      "outline-variant": "#e7bdb9",
                      "surface-container-low": "#f4f3f3",
                      "on-tertiary-container": "#fff4f3",
                      "secondary-fixed": "#dfe3e4",
                      "on-error-container": "#93000a",
                      "on-primary-container": "#fff4f3",
                      "secondary": "#5a5f60",
                      "on-secondary-fixed": "#171c1d",
                      "error": "#ba1a1a",
                      "on-background": "#1a1c1c",
                      "surface-tint": "#c00021",
                      "error-container": "#ffdad6",
                      "background": "#faf9f9",
                      "surface": "#faf9f9",
                      "tertiary-fixed": "#ffdad8",
                      "primary-fixed": "#ffdad7",
                      "on-surface-variant": "#5d3f3d",
                      "surface-bright": "#faf9f9",
                      "on-tertiary-fixed": "#410007",
                      "surface-container-highest": "#e3e2e2",
                      "surface-dim": "#dadada",
                      "on-error": "#ffffff",
                      "surface-variant": "#e3e2e2",
                      "on-secondary-container": "#5e6364",
                      "on-secondary": "#ffffff",
                      "primary": "#b5001e",
                      "tertiary": "#973a3b",
                      "surface-container-high": "#e8e8e8",
                      "on-primary-fixed-variant": "#930016",
                      "outline": "#926e6c",
                      "on-secondary-fixed-variant": "#434849",
                      "inverse-on-surface": "#f1f0f0",
                      "on-tertiary-fixed-variant": "#7f282b",
                      "inverse-primary": "#ffb3af",
                      "tertiary-container": "#b65151",
                      "on-primary": "#ffffff",
                      "primary-fixed-dim": "#ffb3af",
                      "secondary-container": "#dce0e1",
                      "on-surface": "#1a1c1c",
                      "inverse-surface": "#2f3131"
              },
              "borderRadius": {
                      "DEFAULT": "0.125rem",
                      "lg": "0.25rem",
                      "xl": "0.5rem",
                      "full": "0.75rem"
              },
              "spacing": {
                      "max-width": "1440px",
                      "margin-mobile": "16px",
                      "gutter": "16px",
                      "xs": "4px",
                      "xl": "32px",
                      "lg": "24px",
                      "md": "16px",
                      "base": "4px",
                      "margin-desktop": "32px",
                      "sm": "8px"
              },
              "fontFamily": {
                      "title-md": ["Inter"],
                      "label-md": ["Inter"],
                      "body-lg": ["Inter"],
                      "headline-lg-mobile": ["Inter"],
                      "body-md": ["Inter"],
                      "code-md": ["monospace"],
                      "headline-md": ["Inter"],
                      "display-lg": ["Inter"],
                      "headline-lg": ["Inter"]
              },
              "fontSize": {
                      "title-md": ["18px", {"lineHeight": "24px", "fontWeight": "600"}],
                      "label-md": ["12px", {"lineHeight": "16px", "letterSpacing": "0.05em", "fontWeight": "600"}],
                      "body-lg": ["16px", {"lineHeight": "24px", "fontWeight": "400"}],
                      "headline-lg-mobile": ["24px", {"lineHeight": "32px", "fontWeight": "600"}],
                      "body-md": ["14px", {"lineHeight": "20px", "fontWeight": "400"}],
                      "code-md": ["13px", {"lineHeight": "18px", "fontWeight": "400"}],
                      "headline-md": ["24px", {"lineHeight": "32px", "fontWeight": "600"}],
                      "display-lg": ["48px", {"lineHeight": "56px", "letterSpacing": "-0.02em", "fontWeight": "700"}],
                      "headline-lg": ["32px", {"lineHeight": "40px", "letterSpacing": "-0.01em", "fontWeight": "600"}]
              }
            },
          },
        }
      </script>
</head>
<body class="bg-background text-on-background flex h-screen overflow-hidden">
<!-- SideNavBar -->
<aside class="fixed left-0 top-0 w-[260px] h-full bg-on-secondary-fixed-variant dark:bg-inverse-surface border-r border-outline-variant dark:border-outline flex flex-col py-xl px-md gap-sm z-50">
<div class="mb-lg px-4">
<h1 class="font-title-md text-title-md text-surface-bright font-bold">doHealth</h1>
<p class="text-[10px] uppercase tracking-widest text-secondary-fixed-dim">RFID Control Center</p>
</div>
<nav class="flex flex-col gap-xs flex-1">
<a class="text-secondary-fixed-dim dark:text-secondary-fixed hover:text-surface-bright px-4 py-3 hover:bg-on-secondary-fixed transition-all flex items-center gap-md" href="#">
<span class="material-symbols-outlined" data-icon="dashboard">dashboard</span>
<span class="font-label-md text-label-md">Dashboard</span>
</a>
<a class="text-secondary-fixed-dim dark:text-secondary-fixed hover:text-surface-bright px-4 py-3 hover:bg-on-secondary-fixed transition-all flex items-center gap-md" href="#">
<span class="material-symbols-outlined" data-icon="sensors">sensors</span>
<span class="font-label-md text-label-md">Readers</span>
</a>
<a class="bg-primary-container text-on-primary-container font-bold rounded-lg px-4 py-3 flex items-center gap-md translate-x-1 duration-200" href="#">
<span class="material-symbols-outlined" data-icon="label" style="font-variation-settings: 'FILL' 1;">label</span>
<span class="font-label-md text-label-md">Live Tags</span>
</a>
<a class="text-secondary-fixed-dim dark:text-secondary-fixed hover:text-surface-bright px-4 py-3 hover:bg-on-secondary-fixed transition-all flex items-center gap-md" href="#">
<span class="material-symbols-outlined" data-icon="group">group</span>
<span class="font-label-md text-label-md">Groups</span>
</a>
<a class="text-secondary-fixed-dim dark:text-secondary-fixed hover:text-surface-bright px-4 py-3 hover:bg-on-secondary-fixed transition-all flex items-center gap-md" href="#">
<span class="material-symbols-outlined" data-icon="inventory_2">inventory_2</span>
<span class="font-label-md text-label-md">Inventory Systems</span>
</a>
</nav>
<div class="mt-auto px-4 py-4 flex items-center gap-sm border-t border-white/10">
<div class="w-8 h-8 rounded-full bg-secondary-container flex items-center justify-center text-on-secondary-container">
<span class="material-symbols-outlined text-[20px]" data-icon="account_circle">account_circle</span>
</div>
<div class="flex flex-col">
<span class="text-surface-bright font-bold text-[12px]">Admin User</span>
<span class="text-secondary-fixed-dim text-[10px]">System Administrator</span>
</div>
</div>
</aside>
<!-- Main Content Area -->
<main class="flex-1 ml-[260px] flex flex-col h-full bg-surface-container-low">
<!-- TopAppBar -->
<header class="bg-surface dark:bg-surface-dim border-b border-outline-variant dark:border-outline flex justify-between items-center w-full px-margin-desktop h-16 shrink-0">
<div class="flex items-center gap-lg">
<h2 class="font-headline-md text-headline-md font-bold text-primary dark:text-primary-container">Live Tags</h2>
<div class="flex items-center gap-sm bg-surface-container-highest px-md py-xs rounded-full border border-outline-variant">
<span class="w-2 h-2 rounded-full bg-[#10b981]"></span>
<span class="text-[12px] font-bold text-on-surface">Connected</span>
</div>
</div>
<div class="flex items-center gap-md">
<div class="relative group">
<span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[20px]" data-icon="search">search</span>
<input class="pl-10 pr-4 py-1.5 bg-surface-container rounded border border-outline focus:border-primary focus:ring-1 focus:ring-primary/20 text-body-md outline-none transition-all w-[300px]" placeholder="Search EPC..." type="text"/>
</div>
<div class="flex gap-xs">
<button class="p-2 hover:bg-surface-container-low transition-colors rounded-lg text-primary">
<span class="material-symbols-outlined" data-icon="health_and_safety">health_and_safety</span>
</button>
<button class="p-2 hover:bg-surface-container-low transition-colors rounded-lg text-primary">
<span class="material-symbols-outlined" data-icon="notifications">notifications</span>
</button>
</div>
</div>
</header>
<!-- Dynamic Content Canvas -->
<div class="flex-1 overflow-hidden p-margin-desktop flex flex-col gap-md">
<!-- Quick Metrics Bento -->
<div class="grid grid-cols-12 gap-md shrink-0">
<div class="col-span-3 bg-surface-container-lowest border border-outline-variant p-md rounded-xl shadow-sm">
<div class="flex items-center justify-between mb-xs">
<span class="text-on-surface-variant font-label-md text-label-md uppercase tracking-wider">Unique EPCs (60s)</span>
<span class="material-symbols-outlined text-primary text-[20px]" data-icon="speed">speed</span>
</div>
<div class="flex items-baseline gap-sm">
<span class="font-headline-lg text-headline-lg text-on-surface">1,284</span>
<span class="text-[#10b981] text-[12px] font-bold">+12%</span>
</div>
</div>
<div class="col-span-3 bg-surface-container-lowest border border-outline-variant p-md rounded-xl shadow-sm">
<div class="flex items-center justify-between mb-xs">
<span class="text-on-surface-variant font-label-md text-label-md uppercase tracking-wider">Total Reads/Sec</span>
<span class="material-symbols-outlined text-primary text-[20px]" data-icon="data_usage">data_usage</span>
</div>
<div class="flex items-baseline gap-sm">
<span class="font-headline-lg text-headline-lg text-on-surface">45.2k</span>
<span class="text-on-surface-variant text-[12px]">Avg: 42k</span>
</div>
</div>
<div class="col-span-6 bg-surface-container-lowest border border-outline-variant p-md rounded-xl shadow-sm flex items-center justify-between">
<div>
<span class="text-on-surface-variant font-label-md text-label-md uppercase tracking-wider block mb-sm">Active Readers</span>
<div class="flex gap-xs">
<div class="w-8 h-8 rounded bg-primary-container text-on-primary-container flex items-center justify-center font-bold text-xs">R1</div>
<div class="w-8 h-8 rounded bg-primary-container text-on-primary-container flex items-center justify-center font-bold text-xs">R2</div>
<div class="w-8 h-8 rounded bg-primary-container text-on-primary-container flex items-center justify-center font-bold text-xs">R3</div>
<div class="w-8 h-8 rounded bg-secondary-container text-on-secondary-container flex items-center justify-center font-bold text-xs">R4</div>
<div class="w-8 h-8 rounded bg-primary-container text-on-primary-container flex items-center justify-center font-bold text-xs">R5</div>
</div>
</div>
<div class="text-right">
<button class="bg-primary hover:bg-on-primary-fixed-variant text-on-primary px-lg py-sm rounded-lg font-label-md text-label-md transition-colors flex items-center gap-xs">
<span class="material-symbols-outlined text-[18px]" data-icon="filter_list">filter_list</span>
                            Advanced Filters
                        </button>
</div>
</div>
</div>
<!-- High-Density Monitor -->
<div class="flex-1 bg-inverse-surface rounded-xl border border-outline overflow-hidden flex flex-col shadow-xl">
<div class="bg-[#363b3c] px-md py-sm flex items-center justify-between border-b border-outline/30">
<div class="flex items-center gap-lg">
<span class="text-surface-bright font-bold text-[14px] flex items-center gap-sm">
<span class="w-2 h-2 rounded-full bg-primary animate-pulse"></span>
                            Live Stream
                        </span>
<div class="h-4 w-px bg-outline/30"></div>
<div class="flex gap-md">
<label class="flex items-center gap-xs text-secondary-fixed-dim text-[12px] cursor-pointer hover:text-surface-bright transition-colors">
<input checked="" class="rounded border-outline bg-transparent text-primary focus:ring-0" type="checkbox"/>
                                Reader 01
                            </label>
<label class="flex items-center gap-xs text-secondary-fixed-dim text-[12px] cursor-pointer hover:text-surface-bright transition-colors">
<input checked="" class="rounded border-outline bg-transparent text-primary focus:ring-0" type="checkbox"/>
                                Reader 02
                            </label>
<label class="flex items-center gap-xs text-secondary-fixed-dim text-[12px] cursor-pointer hover:text-surface-bright transition-colors">
<input class="rounded border-outline bg-transparent text-primary focus:ring-0" type="checkbox"/>
                                Reader 03
                            </label>
</div>
</div>
<div class="flex items-center gap-sm">
<span class="text-secondary-fixed-dim text-[11px] font-mono">BUFFER: 500/500</span>
<button class="text-surface-bright hover:bg-white/10 p-1 rounded transition-colors">
<span class="material-symbols-outlined text-[20px]" data-icon="pause">pause</span>
</button>
<button class="text-surface-bright hover:bg-white/10 p-1 rounded transition-colors">
<span class="material-symbols-outlined text-[20px]" data-icon="download">download</span>
</button>
</div>
</div>
<!-- Table Content -->
<div class="flex-1 overflow-y-auto custom-scrollbar">
<table class="w-full text-left border-collapse">
<thead class="sticky top-0 bg-[#363b3c] shadow-sm z-10">
<tr>
<th class="px-md py-xs text-surface-bright font-label-md text-[11px] border-b border-outline/20">TIMESTAMP</th>
<th class="px-md py-xs text-surface-bright font-label-md text-[11px] border-b border-outline/20">EPC (ELECTRONIC PRODUCT CODE)</th>
<th class="px-md py-xs text-surface-bright font-label-md text-[11px] border-b border-outline/20">RSSI</th>
<th class="px-md py-xs text-surface-bright font-label-md text-[11px] border-b border-outline/20">READER</th>
<th class="px-md py-xs text-surface-bright font-label-md text-[11px] border-b border-outline/20">ANT</th>
<th class="px-md py-xs text-surface-bright font-label-md text-[11px] border-b border-outline/20 text-right">READ COUNT</th>
</tr>
</thead>
<tbody class="font-mono text-[13px]">
<!-- Row 1 -->
<tr class="group hover:bg-white/5 transition-colors border-b border-outline/10">
<td class="px-md py-sm text-secondary-fixed-dim">14:02:33.421</td>
<td class="px-md py-sm text-primary-fixed font-bold">E280 6894 0000 5003 2410 A1BC</td>
<td class="px-md py-sm">
<div class="flex items-center gap-sm">
<div class="w-16 h-1.5 bg-outline/20 rounded-full overflow-hidden">
<div class="h-full bg-primary" style="width: 78%;"></div>
</div>
<span class="text-secondary-fixed-dim">-42 dBm</span>
</div>
</td>
<td class="px-md py-sm text-surface-bright">DO-READER-01</td>
<td class="px-md py-sm text-surface-bright">01</td>
<td class="px-md py-sm text-right text-secondary-fixed-dim">124</td>
</tr>
<!-- Row 2 -->
<tr class="group hover:bg-white/5 transition-colors border-b border-outline/10 bg-white/[0.02]">
<td class="px-md py-sm text-secondary-fixed-dim">14:02:33.398</td>
<td class="px-md py-sm text-primary-fixed font-bold">E280 6894 0000 5003 2410 B2D4</td>
<td class="px-md py-sm">
<div class="flex items-center gap-sm">
<div class="w-16 h-1.5 bg-outline/20 rounded-full overflow-hidden">
<div class="h-full bg-primary" style="width: 45%;"></div>
</div>
<span class="text-secondary-fixed-dim">-65 dBm</span>
</div>
</td>
<td class="px-md py-sm text-surface-bright">DO-READER-02</td>
<td class="px-md py-sm text-surface-bright">03</td>
<td class="px-md py-sm text-right text-secondary-fixed-dim">12</td>
</tr>
<!-- Row 3 -->
<tr class="group hover:bg-white/5 transition-colors border-b border-outline/10">
<td class="px-md py-sm text-secondary-fixed-dim">14:02:33.312</td>
<td class="px-md py-sm text-primary-fixed font-bold">3008 33B2 DDD9 0140 0000 0001</td>
<td class="px-md py-sm">
<div class="flex items-center gap-sm">
<div class="w-16 h-1.5 bg-outline/20 rounded-full overflow-hidden">
<div class="h-full bg-primary" style="width: 92%;"></div>
</div>
<span class="text-secondary-fixed-dim">-31 dBm</span>
</div>
</td>
<td class="px-md py-sm text-surface-bright">DO-READER-01</td>
<td class="px-md py-sm text-surface-bright">02</td>
<td class="px-md py-sm text-right text-secondary-fixed-dim">2,491</td>
</tr>
<!-- Row 4 -->
<tr class="group hover:bg-white/5 transition-colors border-b border-outline/10 bg-white/[0.02]">
<td class="px-md py-sm text-secondary-fixed-dim">14:02:33.205</td>
<td class="px-md py-sm text-primary-fixed font-bold">E280 1191 2000 7013 1410 F332</td>
<td class="px-md py-sm">
<div class="flex items-center gap-sm">
<div class="w-16 h-1.5 bg-outline/20 rounded-full overflow-hidden">
<div class="h-full bg-primary" style="width: 30%;"></div>
</div>
<span class="text-secondary-fixed-dim">-78 dBm</span>
</div>
</td>
<td class="px-md py-sm text-surface-bright">DO-READER-05</td>
<td class="px-md py-sm text-surface-bright">01</td>
<td class="px-md py-sm text-right text-secondary-fixed-dim">4</td>
</tr>
<!-- Row 5 -->
<tr class="group hover:bg-white/5 transition-colors border-b border-outline/10">
<td class="px-md py-sm text-secondary-fixed-dim">14:02:32.991</td>
<td class="px-md py-sm text-primary-fixed font-bold">3008 33B2 DDD9 0140 0000 0005</td>
<td class="px-md py-sm">
<div class="flex items-center gap-sm">
<div class="w-16 h-1.5 bg-outline/20 rounded-full overflow-hidden">
<div class="h-full bg-primary" style="width: 85%;"></div>
</div>
<span class="text-secondary-fixed-dim">-38 dBm</span>
</div>
</td>
<td class="px-md py-sm text-surface-bright">DO-READER-01</td>
<td class="px-md py-sm text-surface-bright">01</td>
<td class="px-md py-sm text-right text-secondary-fixed-dim">842</td>
</tr>
<!-- Row 6 -->
<tr class="group hover:bg-white/5 transition-colors border-b border-outline/10 bg-white/[0.02]">
<td class="px-md py-sm text-secondary-fixed-dim">14:02:32.855</td>
<td class="px-md py-sm text-primary-fixed font-bold">E280 6894 0000 5003 2410 C1C1</td>
<td class="px-md py-sm">
<div class="flex items-center gap-sm">
<div class="w-16 h-1.5 bg-outline/20 rounded-full overflow-hidden">
<div class="h-full bg-primary" style="width: 62%;"></div>
</div>
<span class="text-secondary-fixed-dim">-55 dBm</span>
</div>
</td>
<td class="px-md py-sm text-surface-bright">DO-READER-02</td>
<td class="px-md py-sm text-surface-bright">04</td>
<td class="px-md py-sm text-right text-secondary-fixed-dim">33</td>
</tr>
<!-- Row 7 -->
<tr class="group hover:bg-white/5 transition-colors border-b border-outline/10">
<td class="px-md py-sm text-secondary-fixed-dim">14:02:32.742</td>
<td class="px-md py-sm text-primary-fixed font-bold">3008 33B2 DDD9 0140 0000 0009</td>
<td class="px-md py-sm">
<div class="flex items-center gap-sm">
<div class="w-16 h-1.5 bg-outline/20 rounded-full overflow-hidden">
<div class="h-full bg-primary" style="width: 70%;"></div>
</div>
<span class="text-secondary-fixed-dim">-48 dBm</span>
</div>
</td>
<td class="px-md py-sm text-surface-bright">DO-READER-01</td>
<td class="px-md py-sm text-surface-bright">02</td>
<td class="px-md py-sm text-right text-secondary-fixed-dim">1,012</td>
</tr>
<!-- Row 8 -->
<tr class="group hover:bg-white/5 transition-colors border-b border-outline/10 bg-white/[0.02]">
<td class="px-md py-sm text-secondary-fixed-dim">14:02:32.610</td>
<td class="px-md py-sm text-primary-fixed font-bold">E280 6894 0000 5003 2410 D4A2</td>
<td class="px-md py-sm">
<div class="flex items-center gap-sm">
<div class="w-16 h-1.5 bg-outline/20 rounded-full overflow-hidden">
<div class="h-full bg-primary" style="width: 25%;"></div>
</div>
<span class="text-secondary-fixed-dim">-82 dBm</span>
</div>
</td>
<td class="px-md py-sm text-surface-bright">DO-READER-03</td>
<td class="px-md py-sm text-surface-bright">02</td>
<td class="px-md py-sm text-right text-secondary-fixed-dim">2</td>
</tr>
</tbody>
</table>
</div>
<!-- Live Feed Map Preview (Asymmetric Pattern) -->
<div class="h-32 bg-[#2f3131] border-t border-outline/20 flex relative overflow-hidden">
<div class="flex-1 p-md flex flex-col justify-between">
<div>
<span class="text-secondary-fixed-dim text-[10px] uppercase font-bold">Facility Location Hotspots</span>
<h4 class="text-surface-bright font-title-md text-title-md leading-tight">Warehouse Zone A-4</h4>
</div>
<div class="flex gap-md text-[11px]">
<span class="text-[#10b981] flex items-center gap-xs"><span class="w-1.5 h-1.5 rounded-full bg-[#10b981]"></span> High Traffic</span>
<span class="text-primary flex items-center gap-xs"><span class="w-1.5 h-1.5 rounded-full bg-primary"></span> Interference Detected</span>
</div>
</div>
<div class="w-1/3 bg-surface-dim relative">
<img class="w-full h-full object-cover opacity-50 grayscale" data-alt="A technical blueprint overlay of a high-tech warehouse floor plan, illuminated with neon red data points and heat maps. The style is modern industrial with clinical precision, using a dark slate background and high-contrast red accents. Mood is surgical and authoritative, reflecting real-time facility monitoring." src="https://lh3.googleusercontent.com/aida-public/AB6AXuBt31A0yKwoc5Si5scT56MHuMXDSByhkrmDKj7zhk51hjI7sEBMc_c9LCI4VyVNB-zxAYaBYlXpPBX82MwW1MpInOAszh6IsTCdz41iz97T0beP5dqERI6QQCTA3hgZkQRiKInW33zoBoG-zf9H6DYYmZ7K2vp4DkPcR7yOSw8zi0L37sf1odJsIEQdYUMLbPrI5UODv6dBdPx8wMecsW6xFoSHArBKgff12negfaBdzDIhnKZDimujrm1Pgsye1AvZL6hXtRoDcHg"/>
<div class="absolute inset-0 flex items-center justify-center">
<span class="bg-primary/80 px-sm py-xs text-[10px] text-on-primary font-bold rounded">LIVE SCANNING</span>
</div>
</div>
</div>
</div>
</div>
</main>
</body></html>

<!-- Live Tags Monitor -->
<!DOCTYPE html>

<html class="light" lang="en"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&amp;display=swap" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<script id="tailwind-config">
        tailwind.config = {
            darkMode: "class",
            theme: {
                extend: {
                    "colors": {
                        "surface-container": "#eeeeed",
                        "on-primary-fixed": "#410005",
                        "secondary-fixed-dim": "#c3c7c8",
                        "tertiary-fixed-dim": "#ffb3b1",
                        "primary-container": "#e0132c",
                        "surface-container-lowest": "#ffffff",
                        "on-tertiary": "#ffffff",
                        "outline-variant": "#e7bdb9",
                        "surface-container-low": "#f4f3f3",
                        "on-tertiary-container": "#fff4f3",
                        "secondary-fixed": "#dfe3e4",
                        "on-error-container": "#93000a",
                        "on-primary-container": "#fff4f3",
                        "secondary": "#5a5f60",
                        "on-secondary-fixed": "#171c1d",
                        "error": "#ba1a1a",
                        "on-background": "#1a1c1c",
                        "surface-tint": "#c00021",
                        "error-container": "#ffdad6",
                        "background": "#faf9f9",
                        "surface": "#faf9f9",
                        "tertiary-fixed": "#ffdad8",
                        "primary-fixed": "#ffdad7",
                        "on-surface-variant": "#5d3f3d",
                        "surface-bright": "#faf9f9",
                        "on-tertiary-fixed": "#410007",
                        "surface-container-highest": "#e3e2e2",
                        "surface-dim": "#dadada",
                        "on-error": "#ffffff",
                        "surface-variant": "#e3e2e2",
                        "on-secondary-container": "#5e6364",
                        "on-secondary": "#ffffff",
                        "primary": "#b5001e",
                        "tertiary": "#973a3b",
                        "surface-container-high": "#e8e8e8",
                        "on-primary-fixed-variant": "#930016",
                        "outline": "#926e6c",
                        "on-secondary-fixed-variant": "#434849",
                        "inverse-on-surface": "#f1f0f0",
                        "on-tertiary-fixed-variant": "#7f282b",
                        "inverse-primary": "#ffb3af",
                        "tertiary-container": "#b65151",
                        "on-primary": "#ffffff",
                        "primary-fixed-dim": "#ffb3af",
                        "secondary-container": "#dce0e1",
                        "on-surface": "#1a1c1c",
                        "inverse-surface": "#2f3131"
                    },
                    "borderRadius": {
                        "DEFAULT": "0.125rem",
                        "lg": "0.25rem",
                        "xl": "0.5rem",
                        "full": "0.75rem"
                    },
                    "spacing": {
                        "max-width": "1440px",
                        "margin-mobile": "16px",
                        "gutter": "16px",
                        "xs": "4px",
                        "xl": "32px",
                        "lg": "24px",
                        "md": "16px",
                        "base": "4px",
                        "margin-desktop": "32px",
                        "sm": "8px"
                    },
                    "fontFamily": {
                        "title-md": ["Inter"],
                        "label-md": ["Inter"],
                        "body-lg": ["Inter"],
                        "headline-lg-mobile": ["Inter"],
                        "body-md": ["Inter"],
                        "code-md": ["monospace"],
                        "headline-md": ["Inter"],
                        "display-lg": ["Inter"],
                        "headline-lg": ["Inter"]
                    },
                    "fontSize": {
                        "title-md": ["18px", {"lineHeight": "24px", "fontWeight": "600"}],
                        "label-md": ["12px", {"lineHeight": "16px", "letterSpacing": "0.05em", "fontWeight": "600"}],
                        "body-lg": ["16px", {"lineHeight": "24px", "fontWeight": "400"}],
                        "headline-lg-mobile": ["24px", {"lineHeight": "32px", "fontWeight": "600"}],
                        "body-md": ["14px", {"lineHeight": "20px", "fontWeight": "400"}],
                        "code-md": ["13px", {"lineHeight": "18px", "fontWeight": "400"}],
                        "headline-md": ["24px", {"lineHeight": "32px", "fontWeight": "600"}],
                        "display-lg": ["48px", {"lineHeight": "56px", "letterSpacing": "-0.02em", "fontWeight": "700"}],
                        "headline-lg": ["32px", {"lineHeight": "40px", "letterSpacing": "-0.01em", "fontWeight": "600"}]
                    }
                },
            },
        }
    </script>
<style>
        .material-symbols-outlined {
            font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
            display: inline-block;
            line-height: 1;
            text-transform: none;
            letter-spacing: normal;
            word-wrap: normal;
            white-space: nowrap;
            direction: ltr;
        }
        body {
            background-color: #f8f9fa;
        }
    </style>
</head>
<body class="bg-background text-on-surface font-body-md text-body-md overflow-x-hidden">
<!-- Sidebar Navigation Shell -->
<aside class="fixed left-0 top-0 w-[260px] h-full bg-on-secondary-fixed-variant dark:bg-inverse-surface border-r border-outline-variant dark:border-outline flex flex-col py-xl px-md gap-sm z-50">
<div class="mb-lg px-base">
<h1 class="font-title-md text-title-md text-surface-bright font-bold">doHealth</h1>
<p class="text-secondary-fixed-dim text-[11px] uppercase tracking-wider">RFID Control Center</p>
</div>
<nav class="flex flex-col gap-xs flex-1">
<a class="text-secondary-fixed-dim dark:text-secondary-fixed hover:text-surface-bright px-4 py-3 font-label-md text-label-md flex items-center gap-md hover:bg-on-secondary-fixed transition-all group" href="#">
<span class="material-symbols-outlined" data-icon="dashboard">dashboard</span>
                Dashboard
            </a>
<a class="bg-primary-container text-on-primary-container font-bold rounded-lg px-4 py-3 font-label-md text-label-md flex items-center gap-md transition-all translate-x-1" href="#">
<span class="material-symbols-outlined" data-icon="sensors">sensors</span>
                Readers
            </a>
<a class="text-secondary-fixed-dim dark:text-secondary-fixed hover:text-surface-bright px-4 py-3 font-label-md text-label-md flex items-center gap-md hover:bg-on-secondary-fixed transition-all group" href="#">
<span class="material-symbols-outlined" data-icon="label">label</span>
                Live Tags
            </a>
<a class="text-secondary-fixed-dim dark:text-secondary-fixed hover:text-surface-bright px-4 py-3 font-label-md text-label-md flex items-center gap-md hover:bg-on-secondary-fixed transition-all group" href="#">
<span class="material-symbols-outlined" data-icon="group">group</span>
                Groups
            </a>
<a class="text-secondary-fixed-dim dark:text-secondary-fixed hover:text-surface-bright px-4 py-3 font-label-md text-label-md flex items-center gap-md hover:bg-on-secondary-fixed transition-all group" href="#">
<span class="material-symbols-outlined" data-icon="inventory_2">inventory_2</span>
                Inventory Systems
            </a>
</nav>
<div class="mt-auto border-t border-secondary-fixed-dim/20 pt-md px-base flex items-center gap-sm">
<div class="w-10 h-10 rounded-full bg-surface-container-high overflow-hidden border border-outline-variant">
<img alt="System Administrator" data-alt="A professional headshot avatar of a system administrator in a high-tech clinical environment. The lighting is soft and professional, reflecting the modern industrial design with high-key whites and subtle gray backgrounds. The style is sharp, clean, and authoritative." src="https://lh3.googleusercontent.com/aida-public/AB6AXuB_rchWX05aGAndquPkGPeYWp9PsXEdmJ-bykVys58-Zyo6VQpj0X9XCjgiVNrPdtHybTBVuwPSQd2OCOO9Rj9w-u7ACuFTHQYiLhbDAgQcatghOzwTm0-2MvnlenDF3LRNrJW-3U_5v61HT_zV3DR5-BKqqmEIAUyy77FdPZuki2jD0vkZ6Tu42VS-FexuthW8W847nhybsR4kiqiWlnxeyaeU-JYA0CL4j21p-GuRjtyQyclqSPvBUzH_CxplmAtsiF1kDz5uQhk"/>
</div>
<div>
<p class="text-surface-bright font-bold text-xs">Admin Panel</p>
<p class="text-secondary-fixed-dim text-[10px]">Connected</p>
</div>
</div>
</aside>
<!-- Top App Bar -->
<header class="fixed top-0 right-0 left-[260px] h-16 bg-surface dark:bg-surface-dim border-b border-outline-variant dark:border-outline flex justify-between items-center px-margin-desktop z-40">
<div class="flex items-center gap-md">
<h2 class="font-headline-md text-headline-md font-bold text-primary dark:text-primary-container">Reader Management</h2>
</div>
<div class="flex items-center gap-lg">
<div class="relative hidden lg:block">
<span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant" data-icon="search">search</span>
<input class="pl-10 pr-4 py-2 bg-surface-container-low border border-outline rounded-lg text-sm focus:outline-none focus:ring-1 focus:ring-primary w-64 transition-all" placeholder="Search gateways..." type="text"/>
</div>
<div class="flex items-center gap-md text-primary dark:text-primary-fixed-dim">
<button class="hover:bg-surface-container-low p-2 rounded-full transition-colors"><span class="material-symbols-outlined" data-icon="health_and_safety">health_and_safety</span></button>
<button class="hover:bg-surface-container-low p-2 rounded-full transition-colors relative"><span class="material-symbols-outlined" data-icon="notifications">notifications</span><span class="absolute top-1 right-1 w-2 h-2 bg-primary-container rounded-full border border-surface"></span></button>
<button class="hover:bg-surface-container-low p-2 rounded-full transition-colors"><span class="material-symbols-outlined" data-icon="account_circle">account_circle</span></button>
</div>
</div>
</header>
<!-- Main Content Canvas -->
<main class="ml-[260px] pt-24 pb-xl px-margin-desktop min-h-screen max-w-max-width mx-auto">
<!-- Header Actions Area -->
<div class="flex flex-col md:flex-row justify-between items-end md:items-center mb-lg gap-md">
<div>
<p class="text-on-surface-variant font-label-md text-label-md mb-1">NETWORK INFRASTRUCTURE</p>
<h3 class="font-headline-lg text-headline-lg text-on-surface">Active RFID Gateways</h3>
</div>
<button class="bg-primary-container text-on-primary-container px-lg py-3 rounded-lg font-bold flex items-center gap-sm hover:opacity-90 active:scale-95 transition-all shadow-sm">
<span class="material-symbols-outlined" data-icon="add">add</span>
                Add New Reader
            </button>
</div>
<!-- Readers Grid/Table Section -->
<section class="bg-surface-container-lowest border border-outline-variant rounded-xl shadow-[0px_2px_4px_rgba(0,0,0,0.05)] overflow-hidden mb-xl">
<div class="bg-inverse-surface px-md py-3 flex justify-between items-center">
<span class="text-surface-bright font-bold text-xs uppercase tracking-widest">Gateway Registry</span>
<span class="text-secondary-fixed-dim text-xs">Total: 4 Readers</span>
</div>
<div class="overflow-x-auto">
<table class="w-full text-left border-collapse">
<thead class="bg-[#363b3c] text-surface-bright font-label-md text-label-md uppercase tracking-tight">
<tr>
<th class="px-md py-4 font-semibold border-b border-outline-variant">Status</th>
<th class="px-md py-4 font-semibold border-b border-outline-variant">Reader Name</th>
<th class="px-md py-4 font-semibold border-b border-outline-variant">Hostname / IP</th>
<th class="px-md py-4 font-semibold border-b border-outline-variant">Brand</th>
<th class="px-md py-4 font-semibold border-b border-outline-variant text-right">Actions</th>
</tr>
</thead>
<tbody class="divide-y divide-outline-variant">
<!-- Row 1 -->
<tr class="hover:bg-surface-container-low transition-colors group">
<td class="px-md py-4">
<div class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-green-100 text-green-800 text-[11px] font-bold border border-green-200">
<span class="w-2 h-2 rounded-full bg-green-600"></span>
                                    CONNECTED
                                </div>
</td>
<td class="px-md py-4 font-bold text-on-surface">OR-Gateway-01</td>
<td class="px-md py-4 text-on-surface-variant font-code-md text-code-md">192.168.10.45</td>
<td class="px-md py-4">
<div class="flex items-center gap-sm">
<span class="material-symbols-outlined text-primary text-sm" data-icon="settings_input_component">settings_input_component</span>
<span class="font-medium">Impinj SpeedWay</span>
</div>
</td>
<td class="px-md py-4 text-right">
<div class="flex items-center justify-end gap-xs">
<button class="p-2 hover:bg-secondary-container rounded text-secondary-fixed-dim hover:text-on-secondary-fixed transition-colors" title="Antennas"><span class="material-symbols-outlined text-[20px]" data-icon="settings_input_antenna">settings_input_antenna</span></button>
<button class="p-2 hover:bg-secondary-container rounded text-secondary-fixed-dim hover:text-on-secondary-fixed transition-colors" title="Edit"><span class="material-symbols-outlined text-[20px]" data-icon="edit">edit</span></button>
<button class="p-2 hover:bg-red-50 rounded text-red-600 transition-colors" title="Disconnect"><span class="material-symbols-outlined text-[20px]" data-icon="link_off">link_off</span></button>
<button class="p-2 hover:bg-error-container rounded text-error transition-colors" title="Delete"><span class="material-symbols-outlined text-[20px]" data-icon="delete">delete</span></button>
</div>
</td>
</tr>
<!-- Row 2 -->
<tr class="hover:bg-surface-container-low transition-colors group">
<td class="px-md py-4">
<div class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-green-100 text-green-800 text-[11px] font-bold border border-green-200">
<span class="w-2 h-2 rounded-full bg-green-600"></span>
                                    CONNECTED
                                </div>
</td>
<td class="px-md py-4 font-bold text-on-surface">ER-Triage-A</td>
<td class="px-md py-4 text-on-surface-variant font-code-md text-code-md">192.168.10.12</td>
<td class="px-md py-4">
<div class="flex items-center gap-sm">
<span class="material-symbols-outlined text-primary text-sm" data-icon="settings_input_component">settings_input_component</span>
<span class="font-medium">Zebra FX9600</span>
</div>
</td>
<td class="px-md py-4 text-right">
<div class="flex items-center justify-end gap-xs">
<button class="p-2 hover:bg-secondary-container rounded text-secondary-fixed-dim hover:text-on-secondary-fixed transition-colors"><span class="material-symbols-outlined text-[20px]" data-icon="settings_input_antenna">settings_input_antenna</span></button>
<button class="p-2 hover:bg-secondary-container rounded text-secondary-fixed-dim hover:text-on-secondary-fixed transition-colors"><span class="material-symbols-outlined text-[20px]" data-icon="edit">edit</span></button>
<button class="p-2 hover:bg-red-50 rounded text-red-600 transition-colors"><span class="material-symbols-outlined text-[20px]" data-icon="link_off">link_off</span></button>
<button class="p-2 hover:bg-error-container rounded text-error transition-colors"><span class="material-symbols-outlined text-[20px]" data-icon="delete">delete</span></button>
</div>
</td>
</tr>
<!-- Row 3 -->
<tr class="hover:bg-surface-container-low transition-colors group bg-surface-container-low/30">
<td class="px-md py-4">
<div class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-gray-200 text-gray-600 text-[11px] font-bold border border-gray-300">
<span class="w-2 h-2 rounded-full bg-gray-400"></span>
                                    DISCONNECTED
                                </div>
</td>
<td class="px-md py-4 font-bold text-on-surface opacity-60">Storage-North-Hub</td>
<td class="px-md py-4 text-on-surface-variant font-code-md text-code-md opacity-60">10.0.4.112</td>
<td class="px-md py-4 opacity-60">
<div class="flex items-center gap-sm">
<span class="material-symbols-outlined text-primary text-sm" data-icon="settings_input_component">settings_input_component</span>
<span class="font-medium">Impinj R700</span>
</div>
</td>
<td class="px-md py-4 text-right">
<div class="flex items-center justify-end gap-xs">
<button class="p-2 hover:bg-secondary-container rounded text-secondary-fixed-dim hover:text-on-secondary-fixed transition-colors"><span class="material-symbols-outlined text-[20px]" data-icon="settings_input_antenna">settings_input_antenna</span></button>
<button class="p-2 hover:bg-secondary-container rounded text-secondary-fixed-dim hover:text-on-secondary-fixed transition-colors"><span class="material-symbols-outlined text-[20px]" data-icon="edit">edit</span></button>
<button class="p-2 hover:bg-green-50 rounded text-green-600 transition-colors" title="Connect"><span class="material-symbols-outlined text-[20px]" data-icon="link">link</span></button>
<button class="p-2 hover:bg-error-container rounded text-error transition-colors"><span class="material-symbols-outlined text-[20px]" data-icon="delete">delete</span></button>
</div>
</td>
</tr>
</tbody>
</table>
</div>
<div class="px-md py-md bg-surface-container-low flex justify-between items-center border-t border-outline-variant">
<p class="text-xs text-on-surface-variant">Showing 1 to 3 of 4 readers</p>
<div class="flex gap-xs">
<button class="px-3 py-1 border border-outline rounded-lg text-xs font-bold bg-surface hover:bg-surface-container-high transition-colors">Prev</button>
<button class="px-3 py-1 border border-outline rounded-lg text-xs font-bold bg-primary text-on-primary">1</button>
<button class="px-3 py-1 border border-outline rounded-lg text-xs font-bold bg-surface hover:bg-surface-container-high transition-colors">Next</button>
</div>
</div>
</section>
<!-- Reader Groups Section -->
<section>
<div class="flex items-center justify-between mb-md">
<h3 class="font-headline-md text-headline-md text-on-surface flex items-center gap-sm">
<span class="material-symbols-outlined text-primary" data-icon="hub">hub</span>
                    Reader Groups
                </h3>
<button class="text-primary hover:underline font-bold text-sm flex items-center gap-xs">
<span class="material-symbols-outlined text-[18px]" data-icon="group_add">group_add</span>
                    Create New Group
                </button>
</div>
<div class="grid grid-cols-1 lg:grid-cols-3 gap-lg">
<!-- Group Card 1 -->
<div class="bg-surface-container-lowest border border-outline-variant rounded-xl p-md shadow-sm hover:shadow-md transition-shadow">
<div class="flex justify-between items-start mb-sm">
<div class="p-2 bg-primary-fixed rounded-lg">
<span class="material-symbols-outlined text-primary" data-icon="medical_services">medical_services</span>
</div>
<span class="bg-secondary-container px-2 py-1 rounded text-[10px] font-extrabold uppercase">ZONE: CRITICAL</span>
</div>
<h4 class="font-bold text-on-surface text-lg mb-1">Operating Theatres</h4>
<p class="text-on-surface-variant text-sm mb-md h-10 overflow-hidden text-ellipsis">Central surgical department readers monitoring high-value medical assets and surgical kits.</p>
<div class="flex items-center justify-between pt-md border-t border-outline-variant">
<div class="flex items-center gap-sm">
<span class="material-symbols-outlined text-on-surface-variant text-[18px]" data-icon="sensors">sensors</span>
<span class="text-xs font-bold">12 Readers</span>
</div>
<button class="text-xs text-primary font-bold hover:underline">Manage Group</button>
</div>
</div>
<!-- Group Card 2 -->
<div class="bg-surface-container-lowest border border-outline-variant rounded-xl p-md shadow-sm hover:shadow-md transition-shadow">
<div class="flex justify-between items-start mb-sm">
<div class="p-2 bg-secondary-container rounded-lg">
<span class="material-symbols-outlined text-secondary" data-icon="inventory">inventory</span>
</div>
<span class="bg-secondary-container px-2 py-1 rounded text-[10px] font-extrabold uppercase">ZONE: LOGISTICS</span>
</div>
<h4 class="font-bold text-on-surface text-lg mb-1">Main Warehouse</h4>
<p class="text-on-surface-variant text-sm mb-md h-10 overflow-hidden text-ellipsis">Inbound and outbound dock-door readers for inventory replenishment tracking.</p>
<div class="flex items-center justify-between pt-md border-t border-outline-variant">
<div class="flex items-center gap-sm">
<span class="material-symbols-outlined text-on-surface-variant text-[18px]" data-icon="sensors">sensors</span>
<span class="text-xs font-bold">8 Readers</span>
</div>
<button class="text-xs text-primary font-bold hover:underline">Manage Group</button>
</div>
</div>
<!-- Group Card 3 -->
<div class="bg-surface-container-lowest border border-outline-variant rounded-xl p-md shadow-sm hover:shadow-md transition-shadow">
<div class="flex justify-between items-start mb-sm">
<div class="p-2 bg-tertiary-fixed rounded-lg">
<span class="material-symbols-outlined text-tertiary" data-icon="emergency">emergency</span>
</div>
<span class="bg-secondary-container px-2 py-1 rounded text-[10px] font-extrabold uppercase">ZONE: PUBLIC</span>
</div>
<h4 class="font-bold text-on-surface text-lg mb-1">Emergency Admissions</h4>
<p class="text-on-surface-variant text-sm mb-md h-10 overflow-hidden text-ellipsis">Readers tracking patient movement and high-frequency medical equipment in ER.</p>
<div class="flex items-center justify-between pt-md border-t border-outline-variant">
<div class="flex items-center gap-sm">
<span class="material-symbols-outlined text-on-surface-variant text-[18px]" data-icon="sensors">sensors</span>
<span class="text-xs font-bold">5 Readers</span>
</div>
<button class="text-xs text-primary font-bold hover:underline">Manage Group</button>
</div>
</div>
</div>
</section>
<!-- Asymmetric Detail/Log Section (Bento Inspired) -->
<div class="mt-xl grid grid-cols-1 md:grid-cols-4 gap-md">
<div class="md:col-span-3 bg-[#363b3c] rounded-xl p-lg text-surface-bright flex items-center justify-between shadow-lg">
<div>
<h5 class="text-xl font-bold mb-1">System Health Alert</h5>
<p class="text-secondary-fixed-dim text-sm max-w-lg">Reader OR-Gateway-05 has reported fluctuating signal strength on Antenna Port 3. Recommended calibration check.</p>
</div>
<div class="hidden lg:block">
<img alt="Alert Icon" class="w-20 h-20 opacity-40 invert" data-alt="A stylized technical diagnostic icon representing signal fluctuation and system health. The icon is rendered in a minimalist, monolinear style consistent with high-end RFID management software, featuring sharp geometric lines and a medical instrumentation aesthetic." src="https://lh3.googleusercontent.com/aida-public/AB6AXuBBn-UGoUbiq9oJm9KY0cCpmBH6E0yIW-PGQsGypk3JztTxMcX2GcVkuT7Q-zbq7wKjGBgGWM2zq3TCGxyKraIlmdO0S5v2NzBkvNhNXZ8G9nLNLymOKlYLifw-yS35-0VkeEqkRB5sPt2v1i15RRvfRRQ5cqzId6zv6pBP4ODbGfzvm-QaX24NYiCJnl3DCPtyJ8gpfbk6umdHucshzfwcaykJgYyf7WDhKgRMefN8zmB7ai9JgPzDUZNBKkqeEY45ZJPL63oR2VM"/>
</div>
</div>
<div class="md:col-span-1 bg-surface-container border border-outline-variant rounded-xl p-lg flex flex-col justify-center items-center text-center">
<p class="text-on-surface-variant font-label-md text-label-md mb-2">NETWORK UPTIME</p>
<p class="text-4xl font-extrabold text-primary">99.8%</p>
<p class="text-xs text-on-surface-variant mt-2 font-bold uppercase tracking-widest">Last 30 Days</p>
</div>
</div>
</main>
</body></html>

<!-- Reader Management -->
<!DOCTYPE html>

<html class="light" lang="en"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&amp;display=swap" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<style>
    .material-symbols-outlined {
      font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 20;
    }
  </style>
<script id="tailwind-config">
    tailwind.config = {
      darkMode: "class",
      theme: {
        extend: {
          "colors": {
                  "surface-container": "#eeeeed",
                  "on-primary-fixed": "#410005",
                  "secondary-fixed-dim": "#c3c7c8",
                  "tertiary-fixed-dim": "#ffb3b1",
                  "primary-container": "#e0132c",
                  "surface-container-lowest": "#ffffff",
                  "on-tertiary": "#ffffff",
                  "outline-variant": "#e7bdb9",
                  "surface-container-low": "#f4f3f3",
                  "on-tertiary-container": "#fff4f3",
                  "secondary-fixed": "#dfe3e4",
                  "on-error-container": "#93000a",
                  "on-primary-container": "#fff4f3",
                  "secondary": "#5a5f60",
                  "on-secondary-fixed": "#171c1d",
                  "error": "#ba1a1a",
                  "on-background": "#1a1c1c",
                  "surface-tint": "#c00021",
                  "error-container": "#ffdad6",
                  "background": "#faf9f9",
                  "surface": "#faf9f9",
                  "tertiary-fixed": "#ffdad8",
                  "primary-fixed": "#ffdad7",
                  "on-surface-variant": "#5d3f3d",
                  "surface-bright": "#faf9f9",
                  "on-tertiary-fixed": "#410007",
                  "surface-container-highest": "#e3e2e2",
                  "surface-dim": "#dadada",
                  "on-error": "#ffffff",
                  "surface-variant": "#e3e2e2",
                  "on-secondary-container": "#5e6364",
                  "on-secondary": "#ffffff",
                  "primary": "#b5001e",
                  "tertiary": "#973a3b",
                  "surface-container-high": "#e8e8e8",
                  "on-primary-fixed-variant": "#930016",
                  "outline": "#926e6c",
                  "on-secondary-fixed-variant": "#434849",
                  "inverse-on-surface": "#f1f0f0",
                  "on-tertiary-fixed-variant": "#7f282b",
                  "inverse-primary": "#ffb3af",
                  "tertiary-container": "#b65151",
                  "on-primary": "#ffffff",
                  "primary-fixed-dim": "#ffb3af",
                  "secondary-container": "#dce0e1",
                  "on-surface": "#1a1c1c",
                  "inverse-surface": "#2f3131"
          },
          "borderRadius": {
                  "DEFAULT": "0.125rem",
                  "lg": "0.25rem",
                  "xl": "0.5rem",
                  "full": "0.75rem"
          },
          "spacing": {
                  "max-width": "1440px",
                  "margin-mobile": "16px",
                  "gutter": "16px",
                  "xs": "4px",
                  "xl": "32px",
                  "lg": "24px",
                  "md": "16px",
                  "base": "4px",
                  "margin-desktop": "32px",
                  "sm": "8px"
          },
          "fontFamily": {
                  "title-md": ["Inter"],
                  "label-md": ["Inter"],
                  "body-lg": ["Inter"],
                  "headline-lg-mobile": ["Inter"],
                  "body-md": ["Inter"],
                  "code-md": ["monospace"],
                  "headline-md": ["Inter"],
                  "display-lg": ["Inter"],
                  "headline-lg": ["Inter"]
          },
          "fontSize": {
                  "title-md": ["18px", {"lineHeight": "24px", "fontWeight": "600"}],
                  "label-md": ["12px", {"lineHeight": "16px", "letterSpacing": "0.05em", "fontWeight": "600"}],
                  "body-lg": ["16px", {"lineHeight": "24px", "fontWeight": "400"}],
                  "headline-lg-mobile": ["24px", {"lineHeight": "32px", "fontWeight": "600"}],
                  "body-md": ["14px", {"lineHeight": "20px", "fontWeight": "400"}],
                  "code-md": ["13px", {"lineHeight": "18px", "fontWeight": "400"}],
                  "headline-md": ["24px", {"lineHeight": "32px", "fontWeight": "600"}],
                  "display-lg": ["48px", {"lineHeight": "56px", "letterSpacing": "-0.02em", "fontWeight": "700"}],
                  "headline-lg": ["32px", {"lineHeight": "40px", "letterSpacing": "-0.01em", "fontWeight": "600"}]
          }
        },
      },
    }
  </script>
</head>
<body class="bg-background text-on-background font-body-md text-body-md overflow-hidden">
<div class="flex h-screen w-full">
<!-- SideNavBar (Authority Source: JSON) -->
<aside class="fixed left-0 top-0 w-[260px] h-full flex flex-col py-xl px-md gap-sm bg-on-secondary-fixed-variant dark:bg-inverse-surface border-r border-outline-variant dark:border-outline z-50">
<div class="mb-xl px-sm">
<h1 class="font-title-md text-title-md text-surface-bright font-bold">doHealth</h1>
<p class="text-secondary-fixed-dim text-[11px] uppercase tracking-widest mt-xs">RFID Control Center</p>
</div>
<nav class="flex flex-col gap-xs flex-grow">
<a class="flex items-center gap-md text-secondary-fixed-dim dark:text-secondary-fixed hover:text-surface-bright px-4 py-3 hover:bg-on-secondary-fixed transition-all font-label-md text-label-md" href="#">
<span class="material-symbols-outlined">dashboard</span>
          Dashboard
        </a>
<a class="flex items-center gap-md text-secondary-fixed-dim dark:text-secondary-fixed hover:text-surface-bright px-4 py-3 hover:bg-on-secondary-fixed transition-all font-label-md text-label-md" href="#">
<span class="material-symbols-outlined">sensors</span>
          Readers
        </a>
<a class="flex items-center gap-md text-secondary-fixed-dim dark:text-secondary-fixed hover:text-surface-bright px-4 py-3 hover:bg-on-secondary-fixed transition-all font-label-md text-label-md" href="#">
<span class="material-symbols-outlined">label</span>
          Live Tags
        </a>
<a class="flex items-center gap-md text-secondary-fixed-dim dark:text-secondary-fixed hover:text-surface-bright px-4 py-3 hover:bg-on-secondary-fixed transition-all font-label-md text-label-md" href="#">
<span class="material-symbols-outlined">group</span>
          Groups
        </a>
<!-- Active Tab: Inventory Systems -->
<a class="flex items-center gap-md bg-primary-container text-on-primary-container font-bold rounded-lg px-4 py-3 font-label-md text-label-md translate-x-1 duration-200" href="#">
<span class="material-symbols-outlined">inventory_2</span>
          Inventory Systems
        </a>
</nav>
<div class="mt-auto flex items-center gap-sm px-sm pt-md border-t border-secondary-fixed-dim/10">
<div class="w-8 h-8 rounded-full bg-surface-container flex items-center justify-center overflow-hidden">
<img alt="System Administrator" class="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuANZwfAvR9xeeGhQaTxUEipiFkMDd80OjtHXbl8F_cSzFsv-8HhWf4EkaT0pPeNiTGyBcpJO-D6n5yIPhEaPYzf0logDs5VZDJujsylVDCsuAPlh5XYIYm-wioERV-uETxhPn7fxajHZSl-i36XZSmB3AWBUnTZVtFhJfuQ2nZREobMNmuvrAQxguSgUmzGtZkIWb_VLXlHEppDW6I70lvvVodmEhm4DStqcEScRNxJJK7c37FpEAEnFI0kEOoEd3bEeUreXMYt6PI"/>
</div>
<div class="flex flex-col">
<span class="text-surface-bright font-bold text-[13px]">Admin User</span>
<span class="text-secondary-fixed-dim text-[11px]">System Gateway</span>
</div>
</div>
</aside>
<!-- Main Canvas -->
<main class="ml-[260px] flex-grow flex flex-col h-full bg-background overflow-hidden">
<!-- TopAppBar (Authority Source: JSON) -->
<header class="flex justify-between items-center w-full px-margin-desktop h-16 bg-surface dark:bg-surface-dim border-b border-outline-variant dark:border-outline sticky top-0 z-40">
<div class="flex items-center gap-lg">
<h2 class="font-headline-md text-headline-md font-bold text-primary dark:text-primary-container">Inventory Systems</h2>
<div class="hidden md:flex items-center bg-surface-container-low px-md py-xs rounded-full border border-outline-variant/30">
<span class="material-symbols-outlined text-on-surface-variant text-[18px]">search</span>
<input class="bg-transparent border-none focus:ring-0 text-body-md py-1 w-64 text-on-surface" placeholder="Search systems..." type="text"/>
</div>
</div>
<div class="flex items-center gap-md">
<div class="flex items-center gap-xs px-sm py-1 bg-green-100 text-green-800 rounded-full border border-green-200">
<span class="w-2 h-2 rounded-full bg-green-600"></span>
<span class="text-label-md font-bold uppercase tracking-wider">Connected</span>
</div>
<div class="flex items-center gap-sm">
<button class="p-2 text-primary hover:bg-surface-container-low transition-colors rounded-full relative">
<span class="material-symbols-outlined">health_and_safety</span>
</button>
<button class="p-2 text-primary hover:bg-surface-container-low transition-colors rounded-full relative">
<span class="material-symbols-outlined">notifications</span>
<span class="absolute top-2 right-2 w-2 h-2 bg-primary rounded-full border border-surface"></span>
</button>
<button class="p-2 text-primary hover:bg-surface-container-low transition-colors rounded-full">
<span class="material-symbols-outlined">account_circle</span>
</button>
</div>
</div>
</header>
<!-- Content Stage -->
<div class="flex-grow p-margin-desktop overflow-y-auto">
<div class="max-w-max-width mx-auto">
<!-- Header Actions -->
<div class="flex justify-between items-end mb-lg">
<div>
<h3 class="font-headline-lg text-headline-lg text-on-background">Continuous Scan Units</h3>
<p class="text-on-surface-variant mt-xs">Manage real-time RFID inventory gates and mobile scanning clusters.</p>
</div>
<button class="bg-primary hover:bg-primary-container text-on-primary px-lg py-sm rounded-lg font-bold flex items-center gap-sm shadow-sm transition-all active:scale-95">
<span class="material-symbols-outlined">add</span>
              Create New System
            </button>
</div>
<!-- Inventory Table Card -->
<div class="bg-surface-container-lowest border border-outline-variant rounded-xl overflow-hidden shadow-sm">
<table class="w-full text-left border-collapse">
<thead>
<tr class="bg-inverse-surface text-surface-bright">
<th class="px-lg py-md font-label-md text-label-md uppercase tracking-wider border-b border-outline">System ID</th>
<th class="px-lg py-md font-label-md text-label-md uppercase tracking-wider border-b border-outline">System Name</th>
<th class="px-lg py-md font-label-md text-label-md uppercase tracking-wider border-b border-outline">Cycle Time (s)</th>
<th class="px-lg py-md font-label-md text-label-md uppercase tracking-wider border-b border-outline">Network Status</th>
<th class="px-lg py-md font-label-md text-label-md uppercase tracking-wider border-b border-outline text-right">Operational Actions</th>
</tr>
</thead>
<tbody class="divide-y divide-outline-variant">
<!-- Row 1 -->
<tr class="hover:bg-surface-container-low transition-colors">
<td class="px-lg py-lg font-code-md text-code-md text-primary font-bold">INV-GATE-001</td>
<td class="px-lg py-lg">
<div class="flex flex-col">
<span class="font-title-md text-title-md">Main Logistics Portal</span>
<span class="text-label-md text-on-surface-variant">North Warehouse Entrance</span>
</div>
</td>
<td class="px-lg py-lg">
<div class="flex items-center gap-sm">
<span class="material-symbols-outlined text-[16px] text-on-surface-variant">timer</span>
<span class="font-bold">0.45s</span>
</div>
</td>
<td class="px-lg py-lg">
<div class="inline-flex items-center gap-xs px-sm py-1 bg-green-50 text-green-700 rounded-full border border-green-100">
<span class="w-2 h-2 rounded-full bg-green-500 animate-pulse"></span>
<span class="text-label-md font-bold uppercase">Active</span>
</div>
</td>
<td class="px-lg py-lg text-right">
<div class="flex items-center justify-end gap-xs">
<button class="flex items-center gap-xs text-primary hover:bg-primary-container/10 px-sm py-xs rounded font-label-md text-label-md border border-primary/20">
<span class="material-symbols-outlined text-[18px]">visibility</span>
                        View Live EPCs
                      </button>
<button class="p-2 text-on-secondary-fixed-variant hover:bg-surface-container-high rounded" title="Edit Configuration">
<span class="material-symbols-outlined">settings</span>
</button>
<button class="p-2 text-error hover:bg-error-container rounded" title="Delete">
<span class="material-symbols-outlined">delete</span>
</button>
</div>
</td>
</tr>
<!-- Row 2 -->
<tr class="hover:bg-surface-container-low transition-colors">
<td class="px-lg py-lg font-code-md text-code-md text-primary font-bold">INV-HUB-X4</td>
<td class="px-lg py-lg">
<div class="flex flex-col">
<span class="font-title-md text-title-md">Surgical Supply Cluster</span>
<span class="text-label-md text-on-surface-variant">Sterile Zone A-4</span>
</div>
</td>
<td class="px-lg py-lg">
<div class="flex items-center gap-sm">
<span class="material-symbols-outlined text-[16px] text-on-surface-variant">timer</span>
<span class="font-bold">1.20s</span>
</div>
</td>
<td class="px-lg py-lg">
<div class="inline-flex items-center gap-xs px-sm py-1 bg-green-50 text-green-700 rounded-full border border-green-100">
<span class="w-2 h-2 rounded-full bg-green-500 animate-pulse"></span>
<span class="text-label-md font-bold uppercase">Active</span>
</div>
</td>
<td class="px-lg py-lg text-right">
<div class="flex items-center justify-end gap-xs">
<button class="flex items-center gap-xs text-primary hover:bg-primary-container/10 px-sm py-xs rounded font-label-md text-label-md border border-primary/20">
<span class="material-symbols-outlined text-[18px]">visibility</span>
                        View Live EPCs
                      </button>
<button class="p-2 text-on-secondary-fixed-variant hover:bg-surface-container-high rounded">
<span class="material-symbols-outlined">settings</span>
</button>
<button class="p-2 text-error hover:bg-error-container rounded">
<span class="material-symbols-outlined">delete</span>
</button>
</div>
</td>
</tr>
<!-- Row 3 -->
<tr class="hover:bg-surface-container-low transition-colors bg-surface-container-low/30">
<td class="px-lg py-lg font-code-md text-code-md text-primary font-bold opacity-50">INV-MBL-99</td>
<td class="px-lg py-lg">
<div class="flex flex-col opacity-50">
<span class="font-title-md text-title-md">Mobile Asset Tracker</span>
<span class="text-label-md text-on-surface-variant">Maintenance Cart #12</span>
</div>
</td>
<td class="px-lg py-lg">
<div class="flex items-center gap-sm opacity-50">
<span class="material-symbols-outlined text-[16px] text-on-surface-variant">timer</span>
<span class="font-bold">N/A</span>
</div>
</td>
<td class="px-lg py-lg">
<div class="inline-flex items-center gap-xs px-sm py-1 bg-surface-container-highest text-secondary rounded-full border border-outline-variant">
<span class="w-2 h-2 rounded-full bg-secondary"></span>
<span class="text-label-md font-bold uppercase">Inactive</span>
</div>
</td>
<td class="px-lg py-lg text-right">
<div class="flex items-center justify-end gap-xs">
<button class="flex items-center gap-xs text-secondary bg-surface-container-high cursor-not-allowed px-sm py-xs rounded font-label-md text-label-md border border-outline-variant" disabled="">
<span class="material-symbols-outlined text-[18px]">visibility_off</span>
                        View Live EPCs
                      </button>
<button class="p-2 text-on-secondary-fixed-variant hover:bg-surface-container-high rounded">
<span class="material-symbols-outlined">settings</span>
</button>
<button class="p-2 text-error hover:bg-error-container rounded">
<span class="material-symbols-outlined">delete</span>
</button>
</div>
</td>
</tr>
<!-- Row 4 -->
<tr class="hover:bg-surface-container-low transition-colors">
<td class="px-lg py-lg font-code-md text-code-md text-primary font-bold">INV-GATE-002</td>
<td class="px-lg py-lg">
<div class="flex flex-col">
<span class="font-title-md text-title-md">Emergency Dispatch Bay</span>
<span class="text-label-md text-on-surface-variant">Ambulance Loading Dock</span>
</div>
</td>
<td class="px-lg py-lg">
<div class="flex items-center gap-sm">
<span class="material-symbols-outlined text-[16px] text-on-surface-variant">timer</span>
<span class="font-bold">0.82s</span>
</div>
</td>
<td class="px-lg py-lg">
<div class="inline-flex items-center gap-xs px-sm py-1 bg-green-50 text-green-700 rounded-full border border-green-100">
<span class="w-2 h-2 rounded-full bg-green-500 animate-pulse"></span>
<span class="text-label-md font-bold uppercase">Active</span>
</div>
</td>
<td class="px-lg py-lg text-right">
<div class="flex items-center justify-end gap-xs">
<button class="flex items-center gap-xs text-primary hover:bg-primary-container/10 px-sm py-xs rounded font-label-md text-label-md border border-primary/20">
<span class="material-symbols-outlined text-[18px]">visibility</span>
                        View Live EPCs
                      </button>
<button class="p-2 text-on-secondary-fixed-variant hover:bg-surface-container-high rounded">
<span class="material-symbols-outlined">settings</span>
</button>
<button class="p-2 text-error hover:bg-error-container rounded">
<span class="material-symbols-outlined">delete</span>
</button>
</div>
</td>
</tr>
</tbody>
</table>
<!-- Table Footer / Pagination -->
<div class="px-lg py-md bg-surface-container-low flex justify-between items-center border-t border-outline-variant">
<span class="text-label-md text-on-surface-variant font-medium">Showing 4 of 24 active systems</span>
<div class="flex gap-xs">
<button class="p-1 border border-outline-variant rounded hover:bg-surface-container-high">
<span class="material-symbols-outlined">chevron_left</span>
</button>
<button class="p-1 border border-primary bg-primary text-on-primary rounded w-8 font-bold text-label-md">1</button>
<button class="p-1 border border-outline-variant rounded hover:bg-surface-container-high w-8 font-bold text-label-md">2</button>
<button class="p-1 border border-outline-variant rounded hover:bg-surface-container-high w-8 font-bold text-label-md">3</button>
<button class="p-1 border border-outline-variant rounded hover:bg-surface-container-high">
<span class="material-symbols-outlined">chevron_right</span>
</button>
</div>
</div>
</div>
<!-- Bottom Grid Info Section -->
<div class="mt-lg grid grid-cols-1 md:grid-cols-3 gap-lg">
<div class="bg-surface-container border border-outline-variant p-md rounded-lg flex items-start gap-md">
<div class="w-10 h-10 rounded bg-primary-container/10 text-primary flex items-center justify-center">
<span class="material-symbols-outlined">speed</span>
</div>
<div>
<h4 class="font-bold text-on-background">System Performance</h4>
<p class="text-body-md text-on-surface-variant mt-xs">Average cycle time across all active nodes is <span class="font-bold text-primary">0.73s</span>.</p>
</div>
</div>
<div class="bg-surface-container border border-outline-variant p-md rounded-lg flex items-start gap-md">
<div class="w-10 h-10 rounded bg-green-100 text-green-700 flex items-center justify-center">
<span class="material-symbols-outlined">check_circle</span>
</div>
<div>
<h4 class="font-bold text-on-background">Operational Integrity</h4>
<p class="text-body-md text-on-surface-variant mt-xs">All primary gateway portals reporting 100% heartbeat uptime.</p>
</div>
</div>
<div class="bg-surface-container border border-outline-variant p-md rounded-lg flex items-start gap-md">
<div class="w-10 h-10 rounded bg-tertiary-container/20 text-tertiary flex items-center justify-center">
<span class="material-symbols-outlined">sync</span>
</div>
<div>
<h4 class="font-bold text-on-background">Pending Updates</h4>
<p class="text-body-md text-on-surface-variant mt-xs">2 systems scheduled for firmware synchronization at 02:00 AM.</p>
</div>
</div>
</div>
</div>
</div>
</main>
</div>
</body></html>