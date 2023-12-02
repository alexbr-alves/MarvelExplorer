package alexProject.data.local

import alexProject.data.model.ThumbnailModel
import alexProject.data.model.character.CharacterModel
import alexProject.data.model.SizeItens
import alexProject.data.model.comic.ComicModel
import alexProject.data.model.event.EventModel

object DataHome {
    val CHARACTER1 = CharacterModel(
        id = 1009610,
        name = "Spider-Man (Peter Parker)",
        description = "Bitten by a radioactive spider, high school student Peter Parker gained the speed, strength and powers of a spider. Adopting the name Spider-Man, Peter hoped to start a career using his new abilities. Taught that with great power comes great responsibility, Spidey has vowed to use his powers to help people.\"",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b",
            extension = "jpg"
        ),
        SizeItens(
            available = 0
        ),
        SizeItens(
            available = 0
        )
    )

    val CHARACTER2 = CharacterModel(
        id = 1009189,
        name = "Black Widow",
        description = "",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/f/30/50fecad1f395b",
            extension = "jpg"
        ),
        SizeItens(
            available = 0
        ),
        SizeItens(
            available = 0
        )
    )

    val CHARACTER3 = CharacterModel(
        id = 1009368,
        name = "Iron Man",
        description = "Wounded, captured and forced to build a weapon by his enemies, billionaire industrialist Tony Stark instead created an advanced suit of armor to save his life and escape captivity. Now with a new outlook on life, Tony uses his money and intelligence to make the world a safer, better place as Iron Man.",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55",
            extension = "jpg"
        ),
        SizeItens(
            available = 0
        ),
        SizeItens(
            available = 0
        )
    )
    val CHARACTER4 = CharacterModel(
        id = 1009664,
        name = "Thor",
        description = "As the Norse God of thunder and lightning, Thor wields one of the greatest weapons ever made, the enchanted hammer Mjolnir. While others have described Thor as an over-muscled, oafish imbecile, he's quite smart and compassionate. He's self-assured, and he would never, ever stop fighting for a worthwhile cause.",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350",
            extension = "jpg"
        ),
        SizeItens(
            available = 0
        ),
        SizeItens(
            available = 0
        )
    )

    val CHARACTER5 = CharacterModel(
        id = 1009187,
        name = "Black Panther",
        description = "",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/6/60/5261a80a67e7d",
            extension = "jpg"
        ),
        SizeItens(
            available = 0
        ),
        SizeItens(
            available = 0
        )
    )

    val COMIC1 = ComicModel(
        id = 112708,
        title = "Deadpool: Seven Slaughters (2023) #1 (Variant)",
        description = "",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/1/20/65526118d589e",
            extension = "jpg"
        )
    )

    val COMIC2 = ComicModel(
        id = 110577,
        title = "Superior Spider-Man (2023) #3",
        description = "",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/c/03/651c62f787332",
            extension = "jpg"
        )
    )

    val COMIC3 = ComicModel(
        id = 109005,
        title = "Immortal Thor (2023) #6",
        description = "",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/6/10/651c62d5035ce",
            extension = "jpg"
        )
    )

    val COMIC4 = ComicModel(
        id = 105842,
        title = "Daredevil (2023) #5",
        description = "",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/6/90/651c629a96d2b",
            extension = "jpg"
        )
    )

    val COMIC5 = ComicModel(
        id = 104805,
        title = "Black Panther (2023) #8",
        description = "",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/9/30/651c627f73aba",
            extension = "jpg"
        )
    )
    val EVENT1 = EventModel(
        id = 238,
        title = "Civil War",
        description = "After a horrific tragedy raises questions on whether or not super heroes should register with the government, longtime Avengers teammates Captain America and Iron Man end up on opposite sides of the argument! Writer Mark Millar and artist Steve McNiven split the Marvel Universe in two as friend fights friend in one of the most celebrated and successful events of all-time!",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/2/d0/51cb5356c2063",
            extension = "jpg"
        )
    )


    val EVENT2 = EventModel(
        id = 332,
        title = "Dead No More: The Clone Conspiracy",
        description = "The Jackal has returned and initiated the Clone Conspiracy! Spider-Man and his allies must contend with friends and enemies alike back from the dead in a limited series by Dan Slott and Jim Cheung that spills into all of the Spidey titles!",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/a/00/57e94a282c8de",
            extension = "jpg"
        )
    )

    val EVENT3 = EventModel(
        id = 334,
        title = "Inhumans Vs. X-Men",
        description = "With the Inhumans' power-granting Terrigen mists threatening the lives of every mutant on the planet, the X-Men have no choice but to declare war! Writers Jeff Lemire and Charles Soule along with artist Leinil Francis Yu chronicle the conflict with a six-issue limited series kicking off in December 2016!",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/a/03/582b35637d930",
            extension = "jpg"
        )
    )
    val EVENT4 = EventModel(
        id = 314,
        title = "Age of Ultron",
        description = "In a 10-issue event written by Brian Michael Bendis with art by Bryan Hitch, Carlos Pacheco, Brandon Peterson and more, the heroes of the Marvel Universe have already fallen with the robot Ultron remaking the world in his image. The surviving Avengers, X-Men and others must fight through time and space in the hope of defeating their foe by any means necessary!",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/c/10/51ca0fc4c83c8",
            extension = "jpg"
        )
    )

    val EVENT5 = EventModel(
        id = 310,
        title = "Avengers VS X-Men",
        description = "A 12-issue event written by the unprecedented team of Brian Michael Bendis, Jason Aaron, Jonathan Hickman, Ed Brubaker and Matt Fraction with art by the blockbuster trio of John Romita Jr., Olivier Coipel and Adam Kubert, Avengers VS X-Men brings Marvel's biggest characters up against the greatest threat they've ever faced: each other!",
        thumbnailModel = ThumbnailModel(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5109a1f93b543",
            extension = "jpg"
        )
    )


}