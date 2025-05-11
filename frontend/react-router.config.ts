import type { Config } from "@react-router/dev/config";

export default {
  // Config options...
  // Server-side render by default, to enable SPA mode set this to `false`
  // TODO check dockerfile and package.json scripts for static build
  ssr: false,
} satisfies Config;
