import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add() {
        val comment = Comment(1, "Комментарий 1")

        val post1 = WallService.add(Post(1, "Скоро Весна", Likes(12), comment, 20, false, true, null, null))
        val post2 = WallService.add(Post(2, "Скоро Лето", Likes(32), comment, 20, false, true, null, null))

        assertNotEquals(post1.id, post2.id)
    }

    @Test
    fun addPhoto() {

        val photo = Photo(1, "Добавлено фото 1")
        val comment = Comment(1, "Комментарий 1")

        val post1 = WallService.add(Post(1, "Скоро Весна", Likes(12), comment, 20, false, true, null, null))
        val post2 = WallService.add(Post(1, "Скоро Весна", Likes(12), comment, 20, false, true, null, arrayOf(PhotoAttachment(photo))))

        assertNotEquals(post1.attachments, post2.attachments)
    }


    @Test
    fun updateExisting() {

        val service = WallService
        val comment = Comment(1, "Комментарий 1")

        service.add(Post(1, "Скоро Весна", Likes(12), comment, 20, false, true, null, null))
        service.add(Post(1, "Скоро Весна", Likes(32), comment, 20, false, true, null, null))
        service.add(Post(1, "Скоро Весна", Likes(62), comment, 20, false, true, null,null))

        val update = Post(1, "Скоро Весна", Likes(82), comment, 20, false, true, null,null)

        val result = service.update(update)

        assertTrue(result)

    }

    @Test
    fun updateNotExisting() {

        val service = WallService
        val comment = Comment(1, "Комментарий 1")

        service.add(Post(1, "Скоро Весна", Likes(12), comment, 20, false, true, null, null))
        service.add(Post(2, "Скоро Лето", Likes(32), comment, 23, false, true, null,null))
        service.add(Post(3, "Скоро Зима", Likes(2), comment, 27, false, true, null, null))

        val update = Post(4, "Скоро ЧТО", Likes(0), comment, 29, false, true, null,null)

        val result = service.update(update)

        assertFalse(result)
    }

    @Test
    fun addComment() {

        val post1 = Post(1, "Скоро Весна", Likes(12), Comment(1, "Комментарий 1"), 20, false, true, null, null)
        val commentAdded = Comment(2, "Добавлен комментарий 2")

        WallService.add(post1)
        WallService.print()
        WallService.createComment(1, commentAdded)
        WallService.update(post1)
        WallService.print()

        val expected = WallService.add(post1)
        val result = WallService.update(post1)

        assertNotEquals(expected, result)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val commentTest = Comment(5, "Ошибка")
        WallService.createComment(5, commentTest)
    }
}