import { test, expect } from '@playwright/test'

//5.17--------------------------------------------
test.describe('Note app', () => {
  test.beforeEach(async ({ page, request }) => {
    await request.post('http://localhost:3001/api/testing/reset')
    await request.post('http://localhost:3001/api/users', {
      data: {
        name: 'Matti Luukkainen',
        username: 'mluukkai',
        password: 'salainen'
      }
    })

    await page.goto('http://localhost:5173')
  })

  test('Login form is shown', async ({ page }) => {
    await page.goto('http://localhost:5173')

    //const content = await page.content()
    //console.log(content)

    const locator = page.locator('text="log in to application"')

    await locator.waitFor({ state: 'visible' })
    await expect(locator).toBeVisible()
  })

  //5.18--------------------------------------------
  test.describe('Login', () => {
    test('succeeds with correct credentials', async ({ page }) => {
      await page.goto('http://localhost:5173')

      await page.fill('input[name="Username"]', 'mluukkai')
      await page.fill('input[name="Password"]', 'salainen')

      await page.click('button[type="submit"]')

      const userWelcomeMessage = page.locator('text="Matti Luukkainen logged in"')
      await expect(userWelcomeMessage).toBeVisible()
    })

    test('fails with wrong credentials', async ({ page }) => {
      await page.goto('http://localhost:5173')

      await page.fill('input[name="Username"]', 'mluukkai')
      await page.fill('input[name="Password"]', 'einiinsalainen')

      await page.click('button[type="submit"]')

      const errorMessage = page.locator('text="wrong username or password"')
      await expect(errorMessage).toBeVisible()
    })
  })

  //5.19--------------------------------------------
  test.describe('When logged in', () => {
    test.beforeEach(async ({ page }) => {
      await page.goto('http://localhost:5173')
      await page.fill('input[name="Username"]', 'mluukkai')
      await page.fill('input[name="Password"]', 'salainen')
      await page.click('button[type="submit"]')

      const userWelcomeMessage = page.locator('text="Matti Luukkainen logged in"')
      await expect(userWelcomeMessage).toBeVisible()
    })

    test('a new blog can be created', async ({ page }) => {
      await page.click('button:has-text("new blog")')
      await page.fill('input[name="Title"]', 'Test Blog Title')
      await page.fill('input[name="Author"]', 'Test Author')
      await page.fill('input[name="URL"]', 'http://testurl.com')
      await page.click('button[type="submit"]')

      //const postSubmContent = await page.content()
      //console.log(postSubmContent)

      const newBlog = page.locator('div:has-text("Test Blog Title Test Author")').last()
      await expect(newBlog).toBeVisible()
    })



    //------------------------------------------------
  })
})