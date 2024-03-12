data class Post(
    val id: Int,
    val text: String = "",
    val likes: Likes = Likes(0),
    val comment: Comment = Comment(0, "Комментарий 0"),
    val date: Int,
    val canDeleted: Boolean?,
    val canEdited: Boolean?,
    val canPined: Boolean?,
    val attachments: Array<Attachment>?
)

data class Likes(
    val countLikes: Int
)

data class Comment(
    val idComment: Int,
    val textComment: String
)

interface Attachment {
    val type: String
}

class PhotoAttachment(val photo: Photo) : Attachment {
    override val type: String = "photo"
}

class Photo(
    val idPhoto: Int,
    val text: String = "",
)

class VideoAttachment(val video: Video) : Attachment {
    override val type: String = "video"
}

class Video(
    val idVideo: Int,
    val title: String = "",
)

class LinkAttachment(val link: Link) : Attachment {
    override val type: String = "link"
}

class Link(
    val url: String,
    val title: String = "",
)

class NoteAttachment(val note: Note) : Attachment {
    override val type: String = "note"
}

class Note(
    val idNote: Int,
    val text: String = "",
)

class PollAttachment(val poll: Poll) : Attachment {
    override val type: String = "poll"
}

class Poll(
    val idPoll: Int,
    val question: String = "",
)

class PostNotFoundException(message: String) : RuntimeException(message)


object WallService {
    private var posts = arrayOf<Post>()
    private var comments = emptyArray<Comment>()

    private var lastId = 0


    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.id == postId) {
                comments += comment
                return comments.last()
            }
        }
        throw PostNotFoundException("Post with postId $postId is not found")
    }

    fun add(post: Post): Post {
        val newPost = post.copy(
            id = ++lastId,
            likes = post.likes.copy(),
            comment = post.comment.copy(),
            attachments = post.attachments?.copyOf()
        )
        posts += newPost
        return newPost
    }

    fun update(newPost: Post): Boolean {
        for (index in posts.indices) {
            if (posts[index].id == newPost.id) {
                posts[index] = newPost.copy(
                    likes = newPost.likes.copy(),
                    comment = createComment(postId = newPost.id, comments.last()),
                    attachments = newPost.attachments?.copyOf()
                )
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        lastId = 0
    }

    fun print() {
        for (post in posts) {
            print(post)
            println()
        }
        println()
    }
}

fun main() {

    val post1 = Post(1, "Скоро Весна", Likes(12), Comment(1, "комментарий 1"), 20, false, true, null, null)
    val post2 = Post(2, "Скоро Лето", Likes(25), Comment(1, "комментарий 1"), 21, false, true, null, null)
    val post3 = Post(3, "Скоро Зима", Likes(2), Comment(1, "комментарий 1"), 25, false, true, null, null)
    WallService.add(post1)
    WallService.add(post2)
    WallService.add(post3)
    WallService.print()

    val comment2 = Comment(2, "Добавлен комментарий 2")
    val comment3 = Comment(3, "Добавлен комментарий 3")

    WallService.createComment(3, comment2)
    WallService.update(post3)
    WallService.print()

    WallService.createComment(5, comment3)
    WallService.print()

}



