import kecs.KEcs
import kecs.KEcs.dsl.ecs
import kecs.system.System
import kotlin.math.min
import kotlin.random.Random

/**
 * Example of a animal race using a ECS, all animals will race following
 *  a mechanical rabbit as lure.
 *
 * This example is has an inspiration of the classical
 *  horse race example used to teach concurrency and threads.
 *
 * However since we use a ECS everything runs concurrently in a
 *  single thread so we could have thousands of animals racing
 *  without performance impact.
 *
 * The output of this program when running will be something like :
 *
 *   100 animals running....
 *
 *   Race end after 52830 loops
 *
 *   The Winner is Forcibly Vocal Wasp!
 *
 *   Mechanical Rabbit arrived in 5.0s
 *
 *   Final lines:
 *
 *   1st Forcibly Vocal Wasp in 5.47s
 *   2nd Solely Working Guppy in 5.48s
 *   3rd Evenly Factual Cougar in 5.531s
 *   4st Suitably Elegant Piglet in 5.533s
 *   ....
 *   97st Unlikely Assuring Hagfish in 9.864s
 *   98st Extremely Infinite Chipmunk in 9.926s
 *   99st Broadly Major Minnow in 9.954s
 *   100st Violently Charming Kangaroo in 9.974s9s
 *
 **/

// Constants

/** How many animals will we have in our race **/
const val NUM_ANIMALS = 100

/** Minimal Speed of each animal, in ft/s **/
const val MIN_ANIMAL_SPEED = 30.0f
/** Max Speed of each animal, ft/s **/
const val MAX_ANIMAL_SPEED = 55.0f

/** Race length in yards **/
const val RACE_LENGTH_IN_YARDS = 100.0f
/** Race length in feet **/
const val RACE_LENGTH = RACE_LENGTH_IN_YARDS * 3.0f

/** The lure speed, it will reach the end in 5s **/
const val LURE_SPEED = RACE_LENGTH / 5.0f

// Components

/** A lure component, it has just a name **/
data class Lure(val name: String)

/** A animal component, it has just a name **/
data class Animal(val name: String)

/** Movement status, running or stopped **/
enum class MovementStatus {
    Running,
    Stopped
}

/** A movement component, it has an speed, in ft/s, and a status **/
data class Movement(
    val speed: Float,
    var status: MovementStatus = MovementStatus.Running
)

/** A position component, includes how long has taking to be there **/
data class Position(var at: Float, var time: Float = 0.0f)

/** A winner component, contains its name **/
data class Winner(val name: String)

/** Race status, running or ended **/
enum class RaceStatus {
    Running,
    Ended
}

// Helpers

/** get a random Float in a range **/
fun ClosedRange<Float>.random() = start + ((endInclusive - start) * Random.nextFloat())

/** get a random capitalized String from a String List **/
fun List<String>.randomCapitalize(): String {
    return this[Random.nextInt(1, this.size)].capitalize()
}

/** get a random animal name like : Unlikely Assuring Hagfish **/
fun randomAnimalName() = "${adverbs.randomCapitalize()} ${adjectives.randomCapitalize()} ${animals.randomCapitalize()}"

/** get a float with 3 decimals positions **/
fun Float.threeDecimals() = (this * 1000).toInt() / 1000.0f

/** get a string with suffix from a Int like: 1st, 2nd, 3rd.. **/
fun Int.withSuffix() = "$this" + when (this) {
    1 -> "st"
    2 -> "nd"
    3 -> "rd"
    else -> "st"
}

// our race

fun animalRace() {
    // we create our world adding 3 systems, each of them it handle only one concern
    //  - the movement system it take care or moving things, both animals and the lure
    //  - the winner system will take care or knowing which animal won
    //  - the race system will take care to know when the race has ended
    val world = ecs {
        +MovementSystem()
        +WinnerSystem()
        +RaceSystem()
    }

    // we create and entity that has the race status to running
    world.add {
        +RaceStatus.Running
    }

    // we create the lure entity, with him name, at the initial position
    //  and with movement set to the lure speed, we will save the reference
    //  to use it latter
    val lure = world.add {
        +Lure(name = "Mechanical Rabbit")
        +Position(at = 0.0f)
        +Movement(speed = LURE_SPEED)
    }

    // we will create as many entities as animal we need in the race
    for (x in 1..NUM_ANIMALS) {
        // we add an entity that is an animal, with a random name
        //  it will start at the initial position and have a
        //  random speed between the min and max animal speed
        world.add {
            +Animal(name = randomAnimalName())
            +Position(at = 0.0f)
            +Movement(speed = (MIN_ANIMAL_SPEED..MAX_ANIMAL_SPEED).random())
        }
    }

    println("$NUM_ANIMALS animals running....\n")

    // we will count how many update loops we have done
    var loops = 0
    // we will ask the world to return a single component from a single
    //  entity that has a RaceStatus, and end the loop if the race has
    //  ended
    while (world.component<RaceStatus>() != RaceStatus.Ended) {
        loops++
        // triggers the world update, each time it send the delta time from the
        //  last update
        world.update()
    }

    // we will print the total loops, this number will be random since we have
    //  random animal speeds they will take different time to complete the race
    println("Race end after $loops loops\n")

    // we will get from the world the Winner component from a single entity,
    //  it will contain the name of the animal that has won
    val winner = world.component<Winner>()
    println("The Winner is ${winner.name}!\n")

    // we will get the name and time component from our lure entity using it
    //  saved reference, surprisingly it will always take 5s
    val lureName = lure.get<Lure>().name
    val lureTime = lure.get<Position>().time
    println("$lureName arrived in ${lureTime.threeDecimals()}s \n")

    println("Final lines:\n")

    // we will get all entities that has an Animal and a Position and sorted by
    //  the time they take to rich that position
    world.view(Animal::class, Position::class).sortedBy {
        it.get<Position>().time
    }.forEachIndexed { place, it ->
        // get the components of the entity and display it
        val animal = it.get<Animal>()
        val pos = it.get<Position>()
        println("${(place + 1).withSuffix()} ${animal.name} in ${pos.time.threeDecimals()}s")
    }
}

/** The system that move things, either animals or the lure **/
class MovementSystem : System() {
    override fun update(delta: Float, total: Float, ecs: KEcs) {
        // get entities that has position and movement
        ecs.view(Position::class, Movement::class).forEach {
            // get the movement component
            val movement = it.get<Movement>()
            // if we are running
            if (movement.status == MovementStatus.Running) {
                // get the position component
                val position = it.get<Position>()
                // calculate the step base on delta time and speed
                val step = (movement.speed * delta)
                // calculate new position, without passing the end
                position.at = min(position.at + step, RACE_LENGTH)
                // add the time running
                position.time += delta
                // if we are at the end stop
                if (position.at == RACE_LENGTH) {
                    movement.status = MovementStatus.Stopped
                }
            }
        }
    }
}

/** THe System that find a winner, only looking at animals, no lure **/
class WinnerSystem : System() {
    override fun update(delta: Float, total: Float, ecs: KEcs) {
        // if we dont have a winner
        if (!ecs.hasComponent<Winner>()) {
            // get entities that are animal and has position, we
            // dont need movement, neither the lure
            ecs.view(Position::class, Animal::class).forEach {
                val position = it.get<Position>()
                val animal = it.get<Animal>()
                // if we are at the end
                if (position.at == RACE_LENGTH) {
                    // add to the world the winner
                    ecs.add { +Winner(animal.name) }
                    return@update
                }
            }
        }
    }
}

/** This System will check when to stop the race **/
class RaceSystem : System() {
    override fun update(delta: Float, total: Float, ecs: KEcs) {
        // first we will check if we aren't already ended
        if (ecs.component<RaceStatus>() != RaceStatus.Ended) {
            // get from all entities that has movement if they
            //  are all stopped
            val allStopped = ecs.components<Movement>().all {
                it.status == MovementStatus.Stopped
            }
            // if all are stopped
            if (allStopped) {
                // set that the race has ended
                val status = ecs.entity(RaceStatus::class)
                status.set(RaceStatus.Ended)
            }
        }
    }
}

/** just random animals **/
val animals: List<String> = listOf(
    "ox", "ant", "ape", "asp", "bat", "bee", "boa", "bug", "cat", "cod", "cow",
    "cub", "doe", "dog", "eel", "eft", "elf", "elk", "emu", "ewe", "fly", "fox",
    "gar", "gnu", "hen", "hog", "imp", "jay", "kid", "kit", "koi", "lab", "man",
    "owl", "pig", "pug", "pup", "ram", "rat", "ray", "yak", "bass", "bear",
    "bird", "boar", "buck", "bull", "calf", "chow", "clam", "colt", "crab",
    "crow", "dane", "deer", "dodo", "dory", "dove", "drum", "duck", "fawn",
    "fish", "flea", "foal", "fowl", "frog", "gnat", "goat", "grub", "gull",
    "hare", "hawk", "ibex", "joey", "kite", "kiwi", "lamb", "lark", "lion",
    "loon", "lynx", "mako", "mink", "mite", "mole", "moth", "mule", "mutt",
    "newt", "orca", "oryx", "pika", "pony", "puma", "seal", "shad", "slug",
    "sole", "stag", "stud", "swan", "tahr", "teal", "tick", "toad", "tuna",
    "wasp", "wolf", "worm", "wren", "yeti", "adder", "akita", "alien", "aphid",
    "bison", "boxer", "bream", "bunny", "burro", "camel", "chimp", "civet",
    "cobra", "coral", "corgi", "crane", "dingo", "drake", "eagle", "egret",
    "filly", "finch", "gator", "gecko", "ghost", "ghoul", "goose", "guppy",
    "heron", "hippo", "horse", "hound", "husky", "hyena", "koala", "krill",
    "leech", "lemur", "liger", "llama", "louse", "macaw", "midge", "molly",
    "moose", "moray", "mouse", "panda", "perch", "prawn", "quail", "racer",
    "raven", "rhino", "robin", "satyr", "shark", "sheep", "shrew", "skink",
    "skunk", "sloth", "snail", "snake", "snipe", "squid", "stork", "swift",
    "swine", "tapir", "tetra", "tiger", "troll", "trout", "viper", "wahoo",
    "whale", "zebra", "alpaca", "amoeba", "baboon", "badger", "beagle",
    "bedbug", "beetle", "bengal", "bobcat", "caiman", "cattle", "cicada",
    "collie", "condor", "cougar", "coyote", "dassie", "donkey", "dragon",
    "earwig", "falcon", "feline", "ferret", "gannet", "gibbon", "glider",
    "goblin", "gopher", "grouse", "guinea", "hermit", "hornet", "iguana",
    "impala", "insect", "jackal", "jaguar", "jennet", "kitten", "kodiak",
    "lizard", "locust", "maggot", "magpie", "mammal", "mantis", "marlin",
    "marmot", "marten", "martin", "mayfly", "minnow", "monkey", "mullet",
    "muskox", "ocelot", "oriole", "osprey", "oyster", "parrot", "pigeon",
    "piglet", "poodle", "possum", "python", "quagga", "rabbit", "raptor",
    "rodent", "roughy", "salmon", "sawfly", "serval", "shiner", "shrimp",
    "spider", "sponge", "tarpon", "thrush", "tomcat", "toucan", "turkey",
    "turtle", "urchin", "vervet", "walrus", "weasel", "weevil", "wombat",
    "anchovy", "anemone", "bluejay", "buffalo", "bulldog", "buzzard", "caribou",
    "catfish", "chamois", "cheetah", "chicken", "chigger", "cowbird", "crappie",
    "crawdad", "cricket", "dogfish", "dolphin", "firefly", "garfish", "gazelle",
    "gelding", "giraffe", "gobbler", "gorilla", "goshawk", "grackle", "griffon",
    "grizzly", "grouper", "gryphon", "haddock", "hagfish", "halibut", "hamster",
    "herring", "jackass", "javelin", "jawfish", "jaybird", "katydid", "ladybug",
    "lamprey", "lemming", "leopard", "lioness", "lobster", "macaque", "mallard",
    "mammoth", "manatee", "mastiff", "meerkat", "mollusk", "monarch", "mongrel",
    "monitor", "monster", "mudfish", "muskrat", "mustang", "narwhal", "oarfish",
    "octopus", "opossum", "ostrich", "panther", "peacock", "pegasus", "pelican",
    "penguin", "phoenix", "piranha", "polecat", "primate", "quetzal", "raccoon",
    "rattler", "redbird", "redfish", "reptile", "rooster", "sawfish", "sculpin",
    "seagull", "skylark", "snapper", "spaniel", "sparrow", "sunbeam", "sunbird",
    "sunfish", "tadpole", "termite", "terrier", "unicorn", "vulture", "wallaby",
    "walleye", "warthog", "whippet", "wildcat", "aardvark", "airedale",
    "albacore", "anteater", "antelope", "arachnid", "barnacle", "basilisk",
    "blowfish", "bluebird", "bluegill", "bonefish", "bullfrog", "cardinal",
    "chipmunk", "cockatoo", "crawfish", "crayfish", "dinosaur", "doberman",
    "duckling", "elephant", "escargot", "flamingo", "flounder", "foxhound",
    "glowworm", "goldfish", "grubworm", "hedgehog", "honeybee", "hookworm",
    "humpback", "kangaroo", "killdeer", "kingfish", "labrador", "lacewing",
    "ladybird", "lionfish", "longhorn", "mackerel", "malamute", "marmoset",
    "mastodon", "moccasin", "mongoose", "monkfish", "mosquito", "pangolin",
    "parakeet", "pheasant", "pipefish", "platypus", "polliwog", "porpoise",
    "reindeer", "ringtail", "sailfish", "scorpion", "seahorse", "seasnail",
    "sheepdog", "shepherd", "silkworm", "squirrel", "stallion", "starfish",
    "starling", "stingray", "stinkbug", "sturgeon", "terrapin", "titmouse",
    "tortoise", "treefrog", "werewolf", "woodcock"
)

/** just random adjectives **/
val adjectives: List<String> = listOf(
    "able", "above", "absolute", "accepted", "accurate", "ace", "active",
    "actual", "adapted", "adapting", "adequate", "adjusted", "advanced",
    "alert", "alive", "allowed", "allowing", "amazed", "amazing", "ample",
    "amused", "amusing", "apparent", "apt", "arriving", "artistic", "assured",
    "assuring", "awaited", "awake", "aware", "balanced", "becoming", "beloved",
    "better", "big", "blessed", "bold", "boss", "brave", "brief", "bright",
    "bursting", "busy", "calm", "capable", "capital", "careful", "caring",
    "casual", "causal", "central", "certain", "champion", "charmed", "charming",
    "cheerful", "chief", "choice", "civil", "classic", "clean", "clear",
    "clever", "climbing", "close", "closing", "coherent", "comic", "communal",
    "complete", "composed", "concise", "concrete", "content", "cool", "correct",
    "cosmic", "crack", "creative", "credible", "crisp", "crucial", "cuddly",
    "cunning", "curious", "current", "cute", "daring", "darling", "dashing",
    "dear", "decent", "deciding", "deep", "definite", "delicate", "desired",
    "destined", "devoted", "direct", "discrete", "distinct", "diverse",
    "divine", "dominant", "driven", "driving", "dynamic", "eager", "easy",
    "electric", "elegant", "emerging", "eminent", "enabled", "enabling",
    "endless", "engaged", "engaging", "enhanced", "enjoyed", "enormous",
    "enough", "epic", "equal", "equipped", "eternal", "ethical", "evident",
    "evolved", "evolving", "exact", "excited", "exciting", "exotic", "expert",
    "factual", "fair", "faithful", "famous", "fancy", "fast", "feasible",
    "fine", "finer", "firm", "first", "fit", "fitting", "fleet", "flexible",
    "flowing", "fluent", "flying", "fond", "frank", "free", "fresh", "full",
    "fun", "funny", "game", "generous", "gentle", "genuine", "giving", "glad",
    "glorious", "glowing", "golden", "good", "gorgeous", "grand", "grateful",
    "great", "growing", "grown", "guided", "guiding", "handy", "happy", "hardy",
    "harmless", "healthy", "helped", "helpful", "helping", "heroic", "hip",
    "holy", "honest", "hopeful", "hot", "huge", "humane", "humble", "humorous",
    "ideal", "immense", "immortal", "immune", "improved", "in", "included",
    "infinite", "informed", "innocent", "inspired", "integral", "intense",
    "intent", "internal", "intimate", "inviting", "joint", "just", "keen",
    "key", "kind", "knowing", "known", "large", "lasting", "leading",
    "learning", "legal", "legible", "lenient", "liberal", "light", "liked",
    "literate", "live", "living", "logical", "loved", "loving", "loyal",
    "lucky", "magical", "magnetic", "main", "major", "many", "massive",
    "master", "mature", "maximum", "measured", "meet", "merry", "mighty",
    "mint", "model", "modern", "modest", "moral", "more", "moved", "moving",
    "musical", "mutual", "national", "native", "natural", "nearby", "neat",
    "needed", "neutral", "new", "next", "nice", "noble", "normal", "notable",
    "noted", "novel", "obliging", "on", "one", "open", "optimal", "optimum",
    "organic", "oriented", "outgoing", "patient", "peaceful", "perfect", "pet",
    "picked", "pleasant", "pleased", "pleasing", "poetic", "polished", "polite",
    "popular", "positive", "possible", "powerful", "precious", "precise",
    "premium", "prepared", "present", "pretty", "primary", "prime", "pro",
    "probable", "profound", "promoted", "prompt", "proper", "proud", "proven",
    "pumped", "pure", "quality", "quick", "quiet", "rapid", "rare", "rational",
    "ready", "real", "refined", "regular", "related", "relative", "relaxed",
    "relaxing", "relevant", "relieved", "renewed", "renewing", "resolved",
    "rested", "rich", "right", "robust", "romantic", "ruling", "sacred", "safe",
    "saved", "saving", "secure", "select", "selected", "sensible", "set",
    "settled", "settling", "sharing", "sharp", "shining", "simple", "sincere",
    "singular", "skilled", "smart", "smashing", "smiling", "smooth", "social",
    "solid", "sought", "sound", "special", "splendid", "square", "stable",
    "star", "steady", "sterling", "still", "stirred", "stirring", "striking",
    "strong", "stunning", "subtle", "suitable", "suited", "summary", "sunny",
    "super", "superb", "supreme", "sure", "sweeping", "sweet", "talented",
    "teaching", "tender", "thankful", "thorough", "tidy", "tight", "together",
    "tolerant", "top", "topical", "tops", "touched", "touching", "tough",
    "true", "trusted", "trusting", "trusty", "ultimate", "unbiased", "uncommon",
    "unified", "unique", "united", "up", "upright", "upward", "usable",
    "useful", "valid", "valued", "vast", "verified", "viable", "vital", "vocal",
    "wanted", "warm", "wealthy", "welcome", "welcomed", "well", "whole",
    "willing", "winning", "wired", "wise", "witty", "wondrous", "workable",
    "working", "worthy"
)

/** just random adverbs **/
val adverbs: List<String> = listOf(
    "abnormally", "absolutely", "accurately", "actively", "actually",
    "adequately", "admittedly", "adversely", "allegedly", "amazingly",
    "annually", "apparently", "arguably", "awfully", "badly", "barely",
    "basically", "blatantly", "blindly", "briefly", "brightly", "broadly",
    "carefully", "centrally", "certainly", "cheaply", "cleanly", "clearly",
    "closely", "commonly", "completely", "constantly", "conversely",
    "correctly", "curiously", "currently", "daily", "deadly", "deeply",
    "definitely", "directly", "distinctly", "duly", "eagerly", "early",
    "easily", "eminently", "endlessly", "enormously", "entirely", "equally",
    "especially", "evenly", "evidently", "exactly", "explicitly", "externally",
    "extremely", "factually", "fairly", "finally", "firmly", "firstly",
    "forcibly", "formally", "formerly", "frankly", "freely", "frequently",
    "friendly", "fully", "generally", "gently", "genuinely", "ghastly",
    "gladly", "globally", "gradually", "gratefully", "greatly", "grossly",
    "happily", "hardly", "heartily", "heavily", "hideously", "highly",
    "honestly", "hopefully", "hopelessly", "horribly", "hugely", "humbly",
    "ideally", "illegally", "immensely", "implicitly", "incredibly",
    "indirectly", "infinitely", "informally", "inherently", "initially",
    "instantly", "intensely", "internally", "jointly", "jolly", "kindly",
    "largely", "lately", "legally", "lightly", "likely", "literally", "lively",
    "locally", "logically", "loosely", "loudly", "lovely", "luckily", "mainly",
    "manually", "marginally", "mentally", "merely", "mildly", "miserably",
    "mistakenly", "moderately", "monthly", "morally", "mostly", "multiply",
    "mutually", "namely", "nationally", "naturally", "nearly", "neatly",
    "needlessly", "newly", "nicely", "nominally", "normally", "notably",
    "noticeably", "obviously", "oddly", "officially", "only", "openly",
    "optionally", "overly", "painfully", "partially", "partly", "perfectly",
    "personally", "physically", "plainly", "pleasantly", "poorly", "positively",
    "possibly", "precisely", "preferably", "presently", "presumably",
    "previously", "primarily", "privately", "probably", "promptly", "properly",
    "publicly", "purely", "quickly", "quietly", "radically", "randomly",
    "rapidly", "rarely", "rationally", "readily", "really", "reasonably",
    "recently", "regularly", "reliably", "remarkably", "remotely", "repeatedly",
    "rightly", "roughly", "routinely", "sadly", "safely", "scarcely",
    "secondly", "secretly", "seemingly", "sensibly", "separately", "seriously",
    "severely", "sharply", "shortly", "similarly", "simply", "sincerely",
    "singularly", "slightly", "slowly", "smoothly", "socially", "solely",
    "specially", "steadily", "strangely", "strictly", "strongly", "subtly",
    "suddenly", "suitably", "supposedly", "surely", "terminally", "terribly",
    "thankfully", "thoroughly", "tightly", "totally", "trivially", "truly",
    "typically", "ultimately", "unduly", "uniformly", "uniquely", "unlikely",
    "urgently", "usefully", "usually", "utterly", "vaguely", "vastly",
    "verbally", "vertically", "vigorously", "violently", "virtually",
    "visually", "weekly", "wholly", "widely", "wildly", "willingly", "wrongly",
    "yearly"
)
