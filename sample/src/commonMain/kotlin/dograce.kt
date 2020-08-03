import kecs.KEcs
import kecs.KEcs.dsl.ecs
import kecs.system.System
import kotlin.math.min
import kotlin.random.Random

/**
 * Example of a animal race using a ECS
 *  all of then run following a mechanical
 *  rabbit as lure.
 *
 * The output of this will be something like :
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
 *   1st Explicitly Huge Eft in 5.47s
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

const val NUM_ANIMALS = 100
const val MIN_ANIMAL_SPEED = 30.0f
const val MAX_ANIMAL_SPEED = 55.0f
const val RACE_LENGTH_IN_YARDS = 100.0f
const val RACE_LENGTH = RACE_LENGTH_IN_YARDS * 3.0f
const val LURE_SPEED = RACE_LENGTH / 5.0f

data class Lure(val name: String)

data class Animal(val name: String)

enum class MovementStatus {
    Running,
    Stopped
}

data class Movement(
    val speed: Float,
    var status: MovementStatus = MovementStatus.Running
)

data class Position(var at: Float, var time: Float = 0.0f)

data class Winner(val name: String)

enum class RaceStatus {
    Running,
    End
}

fun ClosedRange<Float>.random() = start + ((endInclusive - start) * Random.nextFloat())

fun List<String>.randomCapitalize(): String {
    return this[Random.nextInt(1, this.size)].capitalize()
}

fun randomAnimalName() = "${adverbs.randomCapitalize()} ${adjectives.randomCapitalize()} ${animals.randomCapitalize()}"

fun Float.threeDecimals() = (this * 1000).toInt() / 1000.0f

fun Int.withSuffix() = "$this" + when (this) {
    1 -> "st"
    2 -> "nd"
    3 -> "rd"
    else -> "st"
}

fun animalRace() {
    val world = ecs {
        +MovementSystem()
        +WinnerSystem()
        +RaceSystem()
    }

    world.add {
        +RaceStatus.Running
    }

    val lure = world.add {
        +Lure(name = "Mechanical Rabbit")
        +Position(at = 0.0f)
        +Movement(speed = LURE_SPEED)
    }

    for (x in 1..NUM_ANIMALS) {
        world.add {
            +Animal(name = randomAnimalName())
            +Position(at = 0.0f)
            +Movement(speed = (MIN_ANIMAL_SPEED..MAX_ANIMAL_SPEED).random())
        }
    }

    println("$NUM_ANIMALS animals running....\n")

    var loops = 0
    while (world.component<RaceStatus>() != RaceStatus.End) {
        loops++
        world.update()
    }

    println("Race end after $loops loops\n")

    val winner = world.component<Winner>()
    println("The Winner is ${winner.name}!\n")

    val lureName = lure.get<Lure>().name
    val lureTime = lure.get<Position>().time
    println("$lureName arrived in ${lureTime.threeDecimals()}s \n")

    println("Final lines:\n")

    world.view(Animal::class, Position::class).sortedBy {
        it.get<Position>().time
    }.forEachIndexed { place, it ->
        val animal = it.get<Animal>()
        val pos = it.get<Position>()
        println("${(place + 1).withSuffix()} ${animal.name} in ${pos.time.threeDecimals()}s")
    }
}

class MovementSystem : System() {
    override fun update(delta: Float, total: Float, ecs: KEcs) {
        ecs.view(Position::class, Movement::class).forEach {
            val movement = it.get<Movement>()
            if (movement.status == MovementStatus.Running) {
                val position = it.get<Position>()
                val step = (movement.speed * delta)
                position.time += delta
                position.at = min(position.at + step, RACE_LENGTH)
                if (position.at == RACE_LENGTH) {
                    movement.status = MovementStatus.Stopped
                }
            }
        }
    }
}

class WinnerSystem : System() {
    override fun update(delta: Float, total: Float, ecs: KEcs) {
        if (!ecs.hasComponent<Winner>()) {
            ecs.view(Position::class, Animal::class).forEach {
                val position = it.get<Position>()
                val animal = it.get<Animal>()
                if (position.at == RACE_LENGTH) {
                    ecs.add { +Winner(animal.name) }
                    return@update
                }
            }
        }
    }
}

class RaceSystem : System() {
    override fun update(delta: Float, total: Float, ecs: KEcs) {
        if (ecs.component<RaceStatus>() != RaceStatus.End) {
            val allStopped = ecs.components<Movement>().all {
                it.status == MovementStatus.Stopped
            }
            if (allStopped) {
                val status = ecs.entity(RaceStatus::class)
                status.set(RaceStatus.End)
            }
        }
    }
}

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
