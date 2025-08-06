import { Hono, Context } from 'hono'
import { cors } from 'hono/cors'
import { brands, menus } from '@/routes/index.ts'

const app = new Hono()

app.use('*', cors())

app.route('/brands', brands)
app.route('/menus', menus)

app.get('/', (c: Context) => {
  return c.json({ message: 'Menu API is running!' })
})

Deno.serve(app.fetch)