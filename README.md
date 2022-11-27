Comments via cloudflare workers

Original [post](https://blog.cloudflare.com/making-static-sites-dynamic-with-cloudflare-d1/)

Run locally:

```npx wrangler dev --local --persist```

Apply SQL locally:

```npx wrangler d1 execute comments-db --local --file src/schema.sql```

Deploy:

```npx wrangler publish```

