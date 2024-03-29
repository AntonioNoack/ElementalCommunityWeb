import android.view.View
import android.widget.*
import me.antonio.noack.elementalcommunity.AllManager

object R {

    val all = AllManager()

    object color {

        const val colorPrimary = 0xff2ca4ec.toInt()
        const val colorPrimaryDark = 0xff006096.toInt()
        const val colorPrimaryText = 0xff004268.toInt()
        const val colorDiamond = 0xff674280.toInt()
        const val colorDiamond2 = 0xffd1adec.toInt()
        const val colorGreen = 0xff285518.toInt()
        const val colorGreen2 = 0xffb5ecad.toInt()
        const val colorAccent = 0xfff1c190.toInt()

    }

    object string {

        const val app_name = "Elemental Community"
        const val result_name = "Result Name"
        const val cancel = "Cancel"
        const val submit = "Submit"
        const val result_unknown_wanna_add_your_own = "Result unknown! Wanna add your own?"
        const val community_suggestions = "Community suggestions:"
        const val like = "Like"
        const val dislike = "Dislike"
        const val start = "Start"
        const val suggest = "Improve"
        const val back = "Back"
        const val please_choose_name = "Please choose the resulting element!"
        const val please_choose_color = "Please select the element color!"
        const val only_az09 = "Only A-Za-z0-9, space, minus, comma, dot, *, / and ' are allowed!"
        const val sent = "Sent!"
        const val reset_everything = "Reset Everything"
        const val settings = "Settings"
        const val are_you_sure_reset_everything = "Are you sure that you want to delete all your progress?"
        const val tree_view = "Tree View"
        const val random_suggestion = "Random Suggestion"
        const val component_a = "Component A"
        const val component_b = "Component B"
        const val skip_that = "Skip"
        const val favourites = "Favourites: #count"
        const val frequency_of_asking_title = "Frequency of asking for suggestions: #frequency"
        const val no_result_found = "No result was found!"
        const val show_unlock_counter = "Show unlock counter"
        const val diamond_example = "512"
        const val recipe_lookup = "Recipe Lookup:"
        const val recipe_lookup_short = "lookup"
        const val lookup_cost = "10 / lookup"
        const val cost_template = "#cost / #name"
        const val upload_progress_to_server = "Upload progress to server"
        const val download_progress_from_server = "Download progress from server"
        const val save_progress = "Save progress"
        const val load_progress = "Load progress"
        const val save_progress_to_file = "Save progress to file"
        const val load_progress_from_file = "Load progress from file"
        const val please_enter_the_password = "Please enter the password!"
        const val password = "Password"
        const val ok = "OK"
        const val override_old_data = "Delete old achievements"
        const val merge_achievements = "Merge achievements"
        const val success = "Success!"
        const val this_is_your_password = "This is your password:"
        const val choose = "Choose"
        const val please_select_the_file = "Please select the file"
        const val save_progress_to_name = "Save progress to #name"
        const val load_progress_from_name = "Load progress from #name"
        const val please_select_a_file = "Please select a file!"
        const val switch_server = "Switch Server (kind of deprecated)"
        const val settings_help = "Hold down long on a button to get more information."
        const val switch_to_default = "Switch to default"
        const val join_that_server = "Join that server"
        const val server_name = "Server Name"
        const val community = "Community"
        const val create_your_own_server = "Create your own server"
        const val create_your_own_server2 = "Create your own server!"
        const val server_creation_message =
            "Creating your own server allows you to have different recipes than on the default server. It might be useful for your own community, like your YouTube channel."
        const val show_element_uuids = "Display element ids"
        const val clear_recipe_cache = "Clear Recipe Cache"
        const val mandala_view = "Mandala View"
        const val search = "Search"
        const val what_you_are_searching = "What you are searching"
        const val offline_mode = "Offline Mode"
        const val background_music_volume = "Background Music Volume: #percent"
        const val itempedia = "Itempedia"
        const val close = "Close"
        const val crafting_count_disclaimer =
            "*Crafting count has been an estimation for many years now, as it isn't tracked 1:1, but more like 1:30."
        const val creation_date_title = "Creation Date:"
        const val crafting_count_title = "Times Crafted* (Result):"
        const val crafting_count_as_component = "Times Crafted* (Component):"
        const val number_of_recipes = "#Recipes (Result):"
        const val times_used_as_component = "#Recipes (Component):"
        const val suggestions_for_recipes = "#Suggestions (Result):"
        const val times_suggested_as_component = "#Suggestions (Component):"
        const val itempedia_stats = "Element Statistics"
        const val unique_identifier = "Unique Identifier:"

    }

    object drawable {

        const val diamond_icon = "drawable/diamond_icon.png"
        const val ic_arrow_back_black_24dp =
            "<svg width=\"24px\" height=\"24px\" viewportWidth=\"24.0\" viewportHeight=\"24.0\"> <path fill=\"rgba(0, 0, 0, 1.0)\" d=\"M20,11H7.83l5.59,-5.59L12,4l-8,8 8,8 1.41,-1.41L7.83,13H20v-2z\"/></svg>"
        const val ic_launcher_background =
            "<svg height=\"108px\" width=\"108px\" viewportHeight=\"108\" viewportWidth=\"108\"> <path fill=\"rgba(202, 190, 147, 1.0)\" d=\"M0,0h108v108h-108z\"/></svg>"
        const val ic_search_black_24dp =
            "<svg width=\"24px\" height=\"24px\" viewportWidth=\"24.0\" viewportHeight=\"24.0\"> <path fill=\"rgba(0, 0, 0, 1.0)\" d=\"M15.5,14h-0.79l-0.28,-0.27C15.41,12.59 16,11.11 16,9.5 16,5.91 13.09,3 9.5,3S3,5.91 3,9.5 5.91,16 9.5,16c1.61,0 3.09,-0.59 4.23,-1.57l0.27,0.28v0.79l5,4.99L20.49,19l-4.99,-5zM9.5,14C7.01,14 5,11.99 5,9.5S7.01,5 9.5,5 14,7.01 14,9.5 11.99,14 9.5,14z\"/></svg>"

    }

    object layout {

        val add_recipe: View by lazy {
            ScrollView(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "match_parent")
                .attr("orientation", "vertical")
                .attr("layout_id", "add_recipe")
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "vertical")
                        .addView(
                            TextView(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.result_unknown_wanna_add_your_own)
                                .attr("textSize", "20sp")
                                .attr("padding", "5dp")
                                .attr("gravity", "center")
                        )
                        .addView(
                            TextView(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "title2")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.community_suggestions)
                                .attr("textSize", "17sp")
                                .attr("padding", "5dp")
                                .attr("gravity", "center")
                        )
                        .addView(
                            HorizontalScrollView(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .addView(
                                    LinearLayout(all, null)
                                        .attr("layout_width", "wrap_content")
                                        .attr("id", "suggestions")
                                        .attr("layout_height", "wrap_content")
                                        .attr("orientation", "horizontal")
                                )
                        )
                        .addView(
                            EditText(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("id", "name")
                                .attr("layout_height", "wrap_content")
                                .attr("padding", "15dp")
                                .attr("hint", string.result_name)
                        )
                        .addView(
                            me.antonio.noack.elementalcommunity.GroupSelectorView(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("id", "colors")
                                .attr("layout_height", "wrap_content")
                        )
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .attr("orientation", "horizontal")
                                .attr("layout_margin", "5dp")
                                .addView(
                                    Button(all, null)
                                        .attr("background", color.colorPrimary)
                                        .attr("layout_width", "0dp")
                                        .attr("id", "submit")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.submit)
                                        .attr("layout_weight", "1")
                                )
                                .addView(
                                    View(all, null)
                                        .attr("layout_width", "5dp")
                                        .attr("layout_height", "5dp")
                                )
                                .addView(
                                    Button(all, null)
                                        .attr("background", color.colorPrimary)
                                        .attr("layout_width", "0dp")
                                        .attr("id", "cancel")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.cancel)
                                        .attr("layout_weight", "1")
                                )
                        )
                )
        }

        val add_recipe_base: View by lazy {
            ScrollView(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "match_parent")
                .attr("orientation", "vertical")
                .attr("layout_id", "add_recipe_base")
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "vertical")
                        .addView(
                            EditText(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("id", "name")
                                .attr("layout_height", "wrap_content")
                                .attr("padding", "15dp")
                                .attr("hint", string.result_name)
                        )
                        .addView(
                            me.antonio.noack.elementalcommunity.GroupSelectorView(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("id", "colors")
                                .attr("layout_height", "wrap_content")
                        )
                )
        }

        val all_pages: View by lazy {
            ViewFlipper(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "match_parent")
                .attr("id", "flipper")
                .attr("layout_id", "all_pages")
                .addView(menu)
                .addView(game)
                .addView(mandala)
                .addView(tree)
                .addView(graph)
                .addView(combiner)
                .addView(itempedia)
                .addView(settings)
        }

        val ask_override: View by lazy {
            LinearLayout(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "wrap_content")
                .attr("padding", "10dp")
                .attr("orientation", "vertical")
                .attr("layout_id", "ask_override")
                .addView(
                    Button(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "copy")
                        .attr("layout_height", "wrap_content")
                        .attr("text", string.override_old_data)
                        .attr("textSize", "30sp")
                        .attr("padding", "10dp")
                        .attr("textAllCaps", "false")
                        .attr("layout_marginTop", "10dp")
                        .attr("gravity", "center")
                        .attr("textColor", color.colorPrimaryText)
                        .attr("fontFamily", "sans-serif")
                )
                .addView(
                    Button(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "merge")
                        .attr("layout_height", "wrap_content")
                        .attr("text", string.merge_achievements)
                        .attr("textSize", "30sp")
                        .attr("padding", "10dp")
                        .attr("textAllCaps", "false")
                        .attr("layout_marginTop", "10dp")
                        .attr("gravity", "center")
                        .attr("textColor", color.colorPrimaryText)
                        .attr("fontFamily", "sans-serif")
                )
                .addView(
                    Button(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "back")
                        .attr("layout_height", "wrap_content")
                        .attr("text", string.cancel)
                        .attr("textSize", "30sp")
                        .attr("padding", "10dp")
                        .attr("textAllCaps", "false")
                        .attr("layout_marginTop", "10dp")
                        .attr("gravity", "center")
                        .attr("textColor", color.colorPrimaryText)
                        .attr("fontFamily", "sans-serif")
                )
        }

        val ask_password: View by lazy {
            LinearLayout(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "wrap_content")
                .attr("padding", "10dp")
                .attr("orientation", "vertical")
                .attr("layout_id", "ask_password")
                .addView(
                    FrameLayout(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "vertical")
                        .addView(
                            EditText(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("inputType", "numberPassword")
                                .attr("id", "password")
                                .attr("layout_height", "wrap_content")
                                .attr("textSize", "24sp")
                                .attr("hint", string.please_enter_the_password)
                                .attr("layout_margin", "5dp")
                        )
                )
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "horizontal")
                        .addView(
                            Button(all, null)
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.cancel)
                                .attr("textSize", "30sp")
                                .attr("layout_weight", "1")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "0dp")
                                .attr("id", "back")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                        )
                        .addView(
                            View(all, null)
                                .attr("layout_width", "10dp")
                                .attr("layout_height", "1dp")
                        )
                        .addView(
                            Button(all, null)
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.ok)
                                .attr("textSize", "30sp")
                                .attr("layout_weight", "1")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "0dp")
                                .attr("id", "ok")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                        )
                )
        }

        val combiner: View by lazy {
            LinearLayout(all, null)
                .attr("layout_width", "match_parent")
                .attr("background", color.colorPrimaryDark)
                .attr("id", "combinerLayout")
                .attr("layout_height", "match_parent")
                .attr("orientation", "vertical")
                .attr("layout_id", "combiner")
                .addView(
                    me.antonio.noack.elementalcommunity.LoadingBarView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "5dp")
                )
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("background", color.colorPrimary)
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "horizontal")
                        .addView(
                            ImageView(all, null)
                                .attr("layout_width", "50sp")
                                .attr("id", "backArrow2")
                                .attr("layout_height", "50sp")
                                .attr("contentDescription", string.back)
                                .attr("src", drawable.ic_arrow_back_black_24dp)
                        )
                        .addView(
                            Button(all, null)
                                .attr("layout_width", "0dp")
                                .attr("background", color.colorPrimary)
                                .attr("id", "back1")
                                .attr("layout_height", "50sp")
                                .attr("text", string.back)
                                .attr("textSize", "24sp")
                                .attr("padding", "5dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_weight", "1")
                                .attr("gravity", "center")
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            ImageView(all, null)
                                .attr("layout_width", "50sp")
                                .attr("id", "searchButton2")
                                .attr("layout_height", "50sp")
                                .attr("contentDescription", string.search)
                                .attr("src", drawable.ic_search_black_24dp)
                        )
                        .addView(
                            EditText(all, null)
                                .attr("layout_width", "0dp")
                                .attr("importantForAutofill", "no")
                                .attr("id", "search2")
                                .attr("inputType", "text")
                                .attr("layout_height", "50sp")
                                .attr("maxLines", "1")
                                .attr("hint", string.what_you_are_searching)
                                .attr("layout_weight", "3")
                                .attr("visibility", "gone")
                        )
                        .addView(diamond_bar)
                )
                .addView(
                    me.antonio.noack.elementalcommunity.Combiner(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("id", "combiner")
                        .attr("layout_height", "match_parent")
                )
        }

        val diamond_bar: View by lazy {
            LinearLayout(all, null)
                .attr("background", color.colorDiamond)
                .attr("layout_width", "wrap_content")
                .attr("layout_height", "wrap_content")
                .attr("orientation", "horizontal")
                .attr("layout_id", "diamond_bar")
                .addView(
                    View(all, null)
                        .attr("background", color.colorPrimaryDark)
                        .attr("layout_width", "3dp")
                        .attr("layout_height", "50sp")
                )
                .addView(
                    ImageView(all, null)
                        .attr("layout_width", "50sp")
                        .attr("layout_height", "50sp")
                        .attr("src", drawable.diamond_icon)
                )
                .addView(
                    TextView(all, null)
                        .attr("layout_height", "50sp")
                        .attr("text", string.diamond_example)
                        .attr("textSize", "24sp")
                        .attr("paddingStart", "0dp")
                        .attr("paddingEnd", "5dp")
                        .attr("textColor", color.colorDiamond2)
                        .attr("layout_width", "wrap_content")
                        .attr("id", "diamonds")
                        .attr("paddingLeft", "0dp")
                        .attr("paddingRight", "5dp")
                        .attr("paddingBottom", "5dp")
                        .attr("gravity", "center")
                        .attr("paddingTop", "5dp")
                )
        }

        val game: View by lazy {
            LinearLayout(all, null)
                .attr("layout_width", "match_parent")
                .attr("background", color.colorPrimaryDark)
                .attr("id", "gameLayout")
                .attr("layout_height", "match_parent")
                .attr("orientation", "vertical")
                .attr("layout_id", "game")
                .addView(
                    me.antonio.noack.elementalcommunity.LoadingBarView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "5dp")
                )
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("background", color.colorPrimary)
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "horizontal")
                        .addView(
                            ImageView(all, null)
                                .attr("layout_width", "50sp")
                                .attr("id", "backArrow1")
                                .attr("layout_height", "50sp")
                                .attr("contentDescription", string.back)
                                .attr("src", drawable.ic_arrow_back_black_24dp)
                        )
                        .addView(
                            Button(all, null)
                                .attr("layout_width", "0dp")
                                .attr("background", color.colorPrimary)
                                .attr("id", "back3")
                                .attr("layout_height", "50sp")
                                .attr("text", string.back)
                                .attr("textSize", "24sp")
                                .attr("padding", "5dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_weight", "1")
                                .attr("gravity", "center")
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            ImageView(all, null)
                                .attr("layout_width", "50sp")
                                .attr("id", "searchButton1")
                                .attr("layout_height", "50sp")
                                .attr("src", drawable.ic_search_black_24dp)
                        )
                        .addView(
                            EditText(all, null)
                                .attr("layout_width", "0dp")
                                .attr("importantForAutofill", "no")
                                .attr("id", "search1")
                                .attr("inputType", "text")
                                .attr("layout_height", "50sp")
                                .attr("maxLines", "1")
                                .attr("hint", string.what_you_are_searching)
                                .attr("layout_weight", "3")
                                .attr("visibility", "gone")
                        )
                        .addView(diamond_bar)
                )
                .addView(
                    me.antonio.noack.elementalcommunity.UnlockedRows(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("id", "unlocked")
                        .attr("layout_height", "match_parent")
                )
        }

        val graph: View by lazy {
            LinearLayout(all, null)
                .attr("layout_width", "match_parent")
                .attr("background", color.colorPrimaryDark)
                .attr("id", "graphLayout")
                .attr("layout_height", "match_parent")
                .attr("orientation", "vertical")
                .attr("layout_id", "graph")
                .addView(
                    me.antonio.noack.elementalcommunity.LoadingBarView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "5dp")
                )
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("background", color.colorPrimary)
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "horizontal")
                        .addView(
                            ImageView(all, null)
                                .attr("layout_width", "50sp")
                                .attr("id", "backArrow5")
                                .attr("layout_height", "50sp")
                                .attr("contentDescription", string.back)
                                .attr("src", drawable.ic_arrow_back_black_24dp)
                        )
                        .addView(
                            Space(all, null)
                                .attr("layout_width", "0dp")
                                .attr("layout_height", "0dp")
                                .attr("layout_weight", "1")
                        )
                        .addView(diamond_bar)
                )
                .addView(
                    me.antonio.noack.elementalcommunity.graph.GraphView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("id", "graph")
                        .attr("layout_height", "match_parent")
                )
        }

        val helper_offer: View by lazy {
            LinearLayout(all, null)
                .attr("background", color.colorDiamond)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "wrap_content")
                .attr("padding", "5dp")
                .attr("layout_marginTop", "5dp")
                .attr("orientation", "horizontal")
                .attr("layout_id", "helper_offer")
                .addView(
                    View(all, null)
                        .attr("layout_width", "0dp")
                        .attr("layout_height", "1dp")
                        .attr("layout_weight", "1")
                )
                .addView(
                    TextView(all, null)
                        .attr("layout_width", "wrap_content")
                        .attr("id", "title")
                        .attr("layout_height", "30sp")
                        .attr("textSize", "15sp")
                        .attr("text", string.recipe_lookup)
                        .attr("padding", "5dp")
                        .attr("textColor", color.colorDiamond2)
                )
                .addView(
                    View(all, null)
                        .attr("layout_width", "5dp")
                        .attr("layout_height", "1dp")
                )
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "wrap_content")
                        .attr("paddingLeft", "5dp")
                        .attr("layout_height", "30sp")
                        .attr("paddingRight", "5dp")
                        .attr("orientation", "horizontal")
                        .addView(
                            ImageView(all, null)
                                .attr("layout_width", "30sp")
                                .attr("layout_height", "30sp")
                                .attr("src", drawable.diamond_icon)
                        )
                        .addView(
                            TextView(all, null)
                                .attr("layout_width", "wrap_content")
                                .attr("id", "cost")
                                .attr("layout_height", "30sp")
                                .attr("textSize", "15sp")
                                .attr("text", string.lookup_cost)
                                .attr("padding", "5sp")
                                .attr("textColor", color.colorDiamond2)
                        )
                )
                .addView(
                    View(all, null)
                        .attr("layout_width", "0dp")
                        .attr("layout_height", "1dp")
                        .attr("layout_weight", "1")
                )
        }

        val helper_search_recipe: View by lazy {
            LinearLayout(all, null)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "wrap_content")
                .attr("orientation", "vertical")
                .attr("layout_id", "helper_search_recipe")
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "horizontal")
                        .addView(
                            ImageView(all, null)
                                .attr("layout_width", "50sp")
                                .attr("layout_height", "50sp")
                                .attr("src", drawable.ic_search_black_24dp)
                        )
                        .addView(
                            EditText(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("id", "search")
                                .attr("layout_height", "50sp")
                                .attr("maxLines", "1")
                                .attr("hint", string.result_name)
                        )
                )
                .addView(
                    LinearLayout(all, null)
                        .attr("background", color.colorPrimaryDark)
                        .attr("layout_width", "match_parent")
                        .attr("id", "previews")
                        .attr("layout_height", "wrap_content")
                        .attr("padding", "10dp")
                        .attr("visibility", "gone")
                        .attr("orientation", "vertical")
                )
        }

        val itempedia: View by lazy {
            LinearLayout(all, null)
                .attr("layout_width", "match_parent")
                .attr("background", color.colorPrimaryText)
                .attr("id", "itempedia")
                .attr("layout_height", "match_parent")
                .attr("orientation", "vertical")
                .attr("layout_id", "itempedia")
                .addView(
                    me.antonio.noack.elementalcommunity.LoadingBarView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "5dp")
                )
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("background", color.colorPrimary)
                        .attr("layout_height", "wrap_content")
                        .attr("layout_marginBottom", "5dp")
                        .attr("orientation", "horizontal")
                        .addView(
                            ImageView(all, null)
                                .attr("layout_width", "50sp")
                                .attr("id", "backArrow6")
                                .attr("layout_height", "50sp")
                                .attr("contentDescription", string.back)
                                .attr("src", drawable.ic_arrow_back_black_24dp)
                        )
                        .addView(
                            Button(all, null)
                                .attr("layout_width", "0dp")
                                .attr("background", color.colorPrimary)
                                .attr("layout_height", "50sp")
                                .attr("text", string.itempedia)
                                .attr("textSize", "24sp")
                                .attr("padding", "5dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_weight", "1")
                                .attr("gravity", "center")
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            View(all, null)
                                .attr("layout_width", "50sp")
                                .attr("layout_height", "50sp")
                        )
                )
                .addView(
                    androidx.recyclerview.widget.RecyclerView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("id", "itempediaElements")
                        .attr("layout_height", "0dp")
                        .attr("layout_weight", "1")
                )
                .addView(
                    HorizontalScrollView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "wrap_content")
                                .attr("id", "pageFlipper")
                                .attr("paddingLeft", "2dp")
                                .attr("layout_height", "wrap_content")
                                .attr("paddingRight", "2dp")
                                .attr("orientation", "horizontal")
                                .addView(itempedia_page)
                                .addView(itempedia_page)
                                .addView(itempedia_page)
                        )
                )
        }

        val itempedia_item: View by lazy {
            ScrollView(all, null)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "wrap_content")
                .attr("layout_id", "itempedia_item")
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("background", color.colorPrimaryText)
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "vertical")
                        .addView(
                            TextView(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.itempedia_stats)
                                .attr("textSize", "20sp")
                                .attr("padding", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", 0x7f6fffff)
                        )
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .addView(
                                    Space(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("layout_height", "1dp")
                                        .attr("layout_weight", "1")
                                )
                                .addView(
                                    me.antonio.noack.elementalcommunity.OneElement(all, null)
                                        .attr("layout_width", "200dp")
                                        .attr("id", "elementView")
                                        .attr("layout_height", "200dp")
                                )
                                .addView(
                                    Space(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("layout_height", "1dp")
                                        .attr("layout_weight", "1")
                                )
                        )
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("paddingLeft", "10dp")
                                .attr("layout_height", "wrap_content")
                                .attr("paddingRight", "10dp")
                                .attr("orientation", "horizontal")
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.unique_identifier)
                                        .attr("textSize", "20sp")
                                        .attr("layout_weight", "1")
                                        .attr("textColor", 0xab9fffff.toInt())
                                )
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "wrap_content")
                                        .attr("id", "uuid")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", "")
                                        .attr("textSize", "20sp")
                                        .attr("gravity", "end")
                                        .attr("textColor", 0xffffffff.toInt())
                                )
                        )
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("paddingLeft", "10dp")
                                .attr("layout_height", "wrap_content")
                                .attr("paddingRight", "10dp")
                                .attr("orientation", "horizontal")
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.creation_date_title)
                                        .attr("textSize", "20sp")
                                        .attr("layout_weight", "1")
                                        .attr("textColor", 0xab9fffff.toInt())
                                )
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "wrap_content")
                                        .attr("id", "creationDate")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", "")
                                        .attr("textSize", "20sp")
                                        .attr("gravity", "end")
                                        .attr("textColor", 0xffffffff.toInt())
                                )
                        )
                        .addView(
                            View(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", 0x33300)
                                .attr("layout_height", "1dp")
                                .attr("layout_margin", "5dp")
                        )
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("paddingLeft", "10dp")
                                .attr("layout_height", "wrap_content")
                                .attr("paddingRight", "10dp")
                                .attr("orientation", "horizontal")
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.crafting_count_title)
                                        .attr("textSize", "20sp")
                                        .attr("layout_weight", "1")
                                        .attr("textColor", 0xab9fffff.toInt())
                                )
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "wrap_content")
                                        .attr("id", "craftingCount")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", "")
                                        .attr("textSize", "20sp")
                                        .attr("gravity", "end")
                                        .attr("textColor", 0xffffffff.toInt())
                                )
                        )
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("paddingLeft", "10dp")
                                .attr("layout_height", "wrap_content")
                                .attr("paddingRight", "10dp")
                                .attr("orientation", "horizontal")
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.crafting_count_as_component)
                                        .attr("textSize", "20sp")
                                        .attr("layout_weight", "1")
                                        .attr("textColor", 0xab9fffff.toInt())
                                )
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "wrap_content")
                                        .attr("id", "craftingCountAsIngredient")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", "")
                                        .attr("textSize", "20sp")
                                        .attr("gravity", "end")
                                        .attr("textColor", 0xffffffff.toInt())
                                )
                        )
                        .addView(
                            View(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", 0x33300)
                                .attr("layout_height", "1dp")
                                .attr("layout_margin", "5dp")
                        )
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("paddingLeft", "10dp")
                                .attr("layout_height", "wrap_content")
                                .attr("paddingRight", "10dp")
                                .attr("orientation", "horizontal")
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.number_of_recipes)
                                        .attr("textSize", "20sp")
                                        .attr("layout_weight", "1")
                                        .attr("textColor", 0xab9fffff.toInt())
                                )
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "wrap_content")
                                        .attr("id", "numRecipes")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", "")
                                        .attr("textSize", "20sp")
                                        .attr("gravity", "end")
                                        .attr("textColor", 0xffffffff.toInt())
                                )
                        )
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("paddingLeft", "10dp")
                                .attr("layout_height", "wrap_content")
                                .attr("paddingRight", "10dp")
                                .attr("orientation", "horizontal")
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.times_used_as_component)
                                        .attr("textSize", "20sp")
                                        .attr("layout_weight", "1")
                                        .attr("textColor", 0xab9fffff.toInt())
                                )
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "wrap_content")
                                        .attr("id", "numIngredients")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", "")
                                        .attr("textSize", "20sp")
                                        .attr("gravity", "end")
                                        .attr("textColor", 0xffffffff.toInt())
                                )
                        )
                        .addView(
                            View(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", 0x33300)
                                .attr("layout_height", "1dp")
                                .attr("layout_margin", "5dp")
                        )
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("paddingLeft", "10dp")
                                .attr("layout_height", "wrap_content")
                                .attr("paddingRight", "10dp")
                                .attr("orientation", "horizontal")
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.suggestions_for_recipes)
                                        .attr("textSize", "20sp")
                                        .attr("layout_weight", "1")
                                        .attr("textColor", 0xab9fffff.toInt())
                                )
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "wrap_content")
                                        .attr("id", "numSuggestedRecipes")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", "")
                                        .attr("textSize", "20sp")
                                        .attr("gravity", "end")
                                        .attr("textColor", 0xffffffff.toInt())
                                )
                        )
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("paddingLeft", "10dp")
                                .attr("layout_height", "wrap_content")
                                .attr("paddingRight", "10dp")
                                .attr("orientation", "horizontal")
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.times_suggested_as_component)
                                        .attr("textSize", "20sp")
                                        .attr("layout_weight", "1")
                                        .attr("textColor", 0xab9fffff.toInt())
                                )
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "wrap_content")
                                        .attr("id", "numSuggestedIngredients")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", "")
                                        .attr("textSize", "20sp")
                                        .attr("gravity", "end")
                                        .attr("textColor", 0xffffffff.toInt())
                                )
                        )
                        .addView(
                            TextView(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.crafting_count_disclaimer)
                                .attr("padding", "10dp")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", 0x897fffff.toInt())
                        )
                        .addView(
                            Button(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "back")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.close)
                                .attr("textSize", "20sp")
                                .attr("padding", "3dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                        )
                )
        }

        val itempedia_page: View by lazy {
            TextView(all, null)
                .attr("layout_height", "50dp")
                .attr("layout_marginLeft", "2dp")
                .attr("text", "423")
                .attr("textSize", "22sp")
                .attr("layout_marginBottom", "4dp")
                .attr("layout_id", "itempedia_page")
                .attr("textColor", 0xffffffff.toInt())
                .attr("layout_width", "50dp")
                .attr("background", color.colorPrimary)
                .attr("id", "page1")
                .attr("layout_marginTop", "4dp")
                .attr("gravity", "center")
                .attr("layout_marginRight", "2dp")
        }

        val mandala: View by lazy {
            LinearLayout(all, null)
                .attr("layout_width", "match_parent")
                .attr("id", "mandalaLayout")
                .attr("layout_height", "match_parent")
                .attr("orientation", "vertical")
                .attr("layout_id", "mandala")
                .addView(
                    me.antonio.noack.elementalcommunity.LoadingBarView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "5dp")
                )
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("background", color.colorPrimary)
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "horizontal")
                        .addView(
                            ImageView(all, null)
                                .attr("layout_width", "50sp")
                                .attr("id", "backArrow4")
                                .attr("layout_height", "50sp")
                                .attr("contentDescription", string.back)
                                .attr("src", drawable.ic_arrow_back_black_24dp)
                        )
                        .addView(
                            View(all, null)
                                .attr("layout_width", "0dp")
                                .attr("layout_height", "1dp")
                                .attr("layout_weight", "1")
                        )
                        .addView(diamond_bar)
                )
                .addView(
                    me.antonio.noack.elementalcommunity.mandala.MandalaView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("id", "tree2")
                        .attr("layout_height", "match_parent")
                )
        }

        val menu: View by lazy {
            FrameLayout(all, null)
                .attr("layout_width", "match_parent")
                .attr("background", 0xffffffff.toInt())
                .attr("id", "menuLayout")
                .attr("layout_height", "match_parent")
                .attr("layout_id", "menu")
                .addView(
                    me.antonio.noack.elementalcommunity.NewsView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("id", "newsView")
                        .attr("layout_height", "match_parent")
                )
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "match_parent")
                        .attr("background", 0x70006096)
                        .attr("id", "appZoom")
                        .attr("orientation", "vertical")
                        .addView(
                            View(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "0dp")
                                .attr("layout_weight", "1")
                        )
                        .addView(menu_content)
                        .addView(
                            View(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "0dp")
                                .attr("layout_weight", "1")
                        )
                )
        }

        val menu_content: View by lazy {
            ScrollView(all, null)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "wrap_content")
                .attr("orientation", "vertical")
                .attr("layout_id", "menu_content")
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "vertical")
                        .addView(
                            TextView(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("textStyle", "bold")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.app_name)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("gravity", "center")
                                .attr("layout_marginBottom", "10dp")
                                .attr("textColor", color.colorAccent)
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "start")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.start)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("gravity", "center")
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "mandalaButton")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.mandala_view)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "treeButton")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.tree_view)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "graphButton")
                                .attr("layout_height", "wrap_content")
                                .attr("text", "Graph View")
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("visibility", "gone")
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "itempediaButton")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.itempedia)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "randomButton")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.random_suggestion)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "suggest")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.suggest)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "settingsButton")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.settings)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("fontFamily", "sans-serif")
                        )
                )
        }

        val progress_load: View by lazy {
            LinearLayout(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "wrap_content")
                .attr("padding", "10dp")
                .attr("orientation", "vertical")
                .attr("layout_id", "progress_load")
                .addView(
                    Button(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "download")
                        .attr("layout_height", "wrap_content")
                        .attr("text", string.download_progress_from_server)
                        .attr("textSize", "30sp")
                        .attr("padding", "10dp")
                        .attr("textAllCaps", "false")
                        .attr("gravity", "center")
                        .attr("textColor", color.colorPrimaryText)
                        .attr("fontFamily", "sans-serif")
                )
                .addView(
                    Button(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "load")
                        .attr("layout_height", "wrap_content")
                        .attr("text", string.load_progress_from_file)
                        .attr("textSize", "30sp")
                        .attr("padding", "10dp")
                        .attr("textAllCaps", "false")
                        .attr("layout_marginTop", "10dp")
                        .attr("gravity", "center")
                        .attr("textColor", color.colorPrimaryText)
                        .attr("fontFamily", "sans-serif")
                )
                .addView(
                    Button(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "back")
                        .attr("layout_height", "wrap_content")
                        .attr("text", string.cancel)
                        .attr("textSize", "30sp")
                        .attr("padding", "10dp")
                        .attr("textAllCaps", "false")
                        .attr("layout_marginTop", "10dp")
                        .attr("gravity", "center")
                        .attr("textColor", color.colorPrimaryText)
                        .attr("fontFamily", "sans-serif")
                )
        }

        val progress_save: View by lazy {
            LinearLayout(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "wrap_content")
                .attr("padding", "10dp")
                .attr("orientation", "vertical")
                .attr("layout_id", "progress_save")
                .addView(
                    Button(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "upload")
                        .attr("layout_height", "wrap_content")
                        .attr("text", string.upload_progress_to_server)
                        .attr("textSize", "30sp")
                        .attr("padding", "10dp")
                        .attr("textAllCaps", "false")
                        .attr("gravity", "center")
                        .attr("textColor", color.colorPrimaryText)
                        .attr("fontFamily", "sans-serif")
                )
                .addView(
                    Button(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "save")
                        .attr("layout_height", "wrap_content")
                        .attr("text", string.save_progress_to_file)
                        .attr("textSize", "30sp")
                        .attr("padding", "10dp")
                        .attr("textAllCaps", "false")
                        .attr("layout_marginTop", "10dp")
                        .attr("gravity", "center")
                        .attr("textColor", color.colorPrimaryText)
                        .attr("fontFamily", "sans-serif")
                )
                .addView(
                    Button(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "back")
                        .attr("layout_height", "wrap_content")
                        .attr("text", string.cancel)
                        .attr("textSize", "30sp")
                        .attr("padding", "10dp")
                        .attr("textAllCaps", "false")
                        .attr("layout_marginTop", "10dp")
                        .attr("gravity", "center")
                        .attr("textColor", color.colorPrimaryText)
                        .attr("fontFamily", "sans-serif")
                )
        }

        val random: View by lazy {
            ScrollView(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "match_parent")
                .attr("orientation", "vertical")
                .attr("layout_id", "random")
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "vertical")
                        .addView(
                            LinearLayout(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .attr("padding", "5dp")
                                .attr("orientation", "horizontal")
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.component_a)
                                        .attr("textSize", "20sp")
                                        .attr("layout_weight", "1")
                                        .attr("textAlignment", "center")
                                )
                                .addView(
                                    TextView(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.component_b)
                                        .attr("textSize", "20sp")
                                        .attr("layout_weight", "1")
                                        .attr("textAlignment", "center")
                                )
                        )
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .attr("orientation", "horizontal")
                                .attr("layout_margin", "5dp")
                                .addView(
                                    me.antonio.noack.elementalcommunity.OneElement(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("id", "first")
                                        .attr("layout_height", "wrap_content")
                                        .attr("layout_weight", "1")
                                        .attr("gravity", "center")
                                )
                                .addView(
                                    me.antonio.noack.elementalcommunity.OneElement(all, null)
                                        .attr("layout_width", "0dp")
                                        .attr("id", "second")
                                        .attr("layout_height", "wrap_content")
                                        .attr("layout_weight", "1")
                                        .attr("gravity", "center")
                                )
                        )
                        .addView(
                            EditText(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("id", "name")
                                .attr("layout_height", "wrap_content")
                                .attr("padding", "15dp")
                                .attr("hint", string.result_name)
                        )
                        .addView(
                            me.antonio.noack.elementalcommunity.GroupSelectorView(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("id", "colors")
                                .attr("layout_height", "wrap_content")
                        )
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .attr("orientation", "horizontal")
                                .attr("layout_margin", "5dp")
                                .addView(
                                    Button(all, null)
                                        .attr("background", color.colorPrimary)
                                        .attr("layout_width", "0dp")
                                        .attr("id", "submit")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.submit)
                                        .attr("layout_weight", "1")
                                )
                                .addView(
                                    View(all, null)
                                        .attr("layout_width", "5dp")
                                        .attr("layout_height", "5dp")
                                )
                                .addView(
                                    Button(all, null)
                                        .attr("background", color.colorPrimary)
                                        .attr("layout_width", "0dp")
                                        .attr("id", "cancel")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.cancel)
                                        .attr("layout_weight", "1")
                                )
                                .addView(
                                    View(all, null)
                                        .attr("layout_width", "5dp")
                                        .attr("layout_height", "5dp")
                                )
                                .addView(
                                    Button(all, null)
                                        .attr("background", color.colorPrimary)
                                        .attr("layout_width", "0dp")
                                        .attr("id", "next")
                                        .attr("layout_height", "wrap_content")
                                        .attr("text", string.skip_that)
                                        .attr("layout_weight", "1")
                                )
                        )
                )
        }

        val recipe_helper: View by lazy {
            LinearLayout(all, null)
                .attr("background", color.colorPrimary)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "wrap_content")
                .attr("padding", "5dp")
                .attr("orientation", "vertical")
                .attr("layout_id", "recipe_helper")
                .addView(
                    LinearLayout(all, null)
                        .attr("background", color.colorDiamond)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .attr("padding", "5dp")
                        .attr("orientation", "horizontal")
                        .addView(
                            View(all, null)
                                .attr("layout_width", "0dp")
                                .attr("layout_height", "1dp")
                                .attr("layout_weight", "1")
                        )
                        .addView(
                            ImageView(all, null)
                                .attr("layout_width", "50sp")
                                .attr("layout_height", "50sp")
                                .attr("src", drawable.diamond_icon)
                        )
                        .addView(
                            TextView(all, null)
                                .attr("layout_width", "wrap_content")
                                .attr("id", "diamonds")
                                .attr("layout_height", "50sp")
                                .attr("text", string.diamond_example)
                                .attr("textSize", "24sp")
                                .attr("padding", "5dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorDiamond2)
                        )
                        .addView(
                            View(all, null)
                                .attr("layout_width", "0dp")
                                .attr("layout_height", "1dp")
                                .attr("layout_weight", "1")
                        )
                )
                .addView(
                    ScrollView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .addView(
                            LinearLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("id", "offers")
                                .attr("layout_height", "wrap_content")
                                .attr("orientation", "vertical")
                        )
                )
        }

        val select_file: View by lazy {
            LinearLayout(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "wrap_content")
                .attr("layout_height", "wrap_content")
                .attr("layout_marginTop", "10dp")
                .attr("orientation", "vertical")
                .attr("layout_id", "select_file")
                .addView(
                    TextView(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "title")
                        .attr("layout_height", "wrap_content")
                        .attr("textSize", "24sp")
                        .attr("text", string.please_select_the_file)
                        .attr("padding", "5dp")
                )
        }

        val select_folder: View by lazy {
            LinearLayout(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "match_parent")
                .attr("padding", "10dp")
                .attr("orientation", "vertical")
                .attr("layout_id", "select_folder")
                .addView(
                    TextView(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "title")
                        .attr("layout_height", "wrap_content")
                        .attr("textSize", "30sp")
                        .attr("text", string.please_select_the_file)
                        .attr("padding", "10dp")
                )
                .addView(
                    TextView(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "path")
                        .attr("layout_height", "wrap_content")
                        .attr("textSize", "17sp")
                        .attr("layout_marginLeft", "10dp")
                        .attr("text", string.app_name)
                        .attr("padding", "10dp")
                        .attr("layout_marginTop", "10dp")
                        .attr("layout_marginRight", "10dp")
                )
                .addView(
                    ScrollView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "0dp")
                        .attr("layout_marginLeft", "10dp")
                        .attr("layout_weight", "1")
                        .attr("layout_marginRight", "10dp")
                        .attr("layout_margin", "10dp")
                        .addView(
                            me.antonio.noack.elementalcommunity.PatchesView(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("id", "fileList")
                                .attr("layout_height", "wrap_content")
                                .attr("orientation", "vertical")
                        )
                )
                .addView(
                    Button(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "select")
                        .attr("layout_height", "wrap_content")
                        .attr("text", string.save_progress_to_name)
                        .attr("textSize", "24sp")
                        .attr("padding", "10dp")
                        .attr("textAllCaps", "false")
                        .attr("gravity", "center")
                        .attr("layout_marginBottom", "10dp")
                        .attr("textColor", color.colorPrimaryText)
                        .attr("fontFamily", "sans-serif")
                )
                .addView(
                    Button(all, null)
                        .attr("background", color.colorPrimary)
                        .attr("layout_width", "match_parent")
                        .attr("id", "back")
                        .attr("layout_height", "wrap_content")
                        .attr("text", string.cancel)
                        .attr("textSize", "24sp")
                        .attr("padding", "10dp")
                        .attr("textAllCaps", "false")
                        .attr("gravity", "center")
                        .attr("textColor", color.colorPrimaryText)
                        .attr("fontFamily", "sans-serif")
                )
        }

        val settings: View by lazy {
            ScrollView(all, null)
                .attr("layout_width", "match_parent")
                .attr("background", color.colorPrimaryDark)
                .attr("id", "settingsLayout")
                .attr("layout_height", "match_parent")
                .attr("orientation", "vertical")
                .attr("layout_id", "settings")
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "vertical")
                        .addView(
                            TextView(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.settings_help)
                                .attr("textSize", "20sp")
                                .attr("padding", "10dp")
                                .attr("layout_marginTop", "5dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimary)
                        )
                        .addView(
                            Button(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorGreen2)
                                .attr("id", "switchServer")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.switch_server)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorGreen)
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            TextView(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "favTitle")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.favourites)
                                .attr("textSize", "20sp")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("paddingTop", "10dp")
                        )
                        .addView(
                            SeekBar(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "favSlider")
                                .attr("layout_height", "20dp")
                                .attr("thumbTint", color.colorPrimaryDark)
                                .attr("progressTint", color.colorPrimaryDark)
                        )
                        .addView(
                            TextView(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "frequencyTitle")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.frequency_of_asking_title)
                                .attr("textSize", "20sp")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("paddingTop", "10dp")
                        )
                        .addView(
                            SeekBar(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "frequencySlider")
                                .attr("layout_height", "20dp")
                                .attr("thumbTint", color.colorPrimaryDark)
                                .attr("progressTint", color.colorPrimaryDark)
                        )
                        .addView(
                            TextView(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "backgroundVolumeTitle")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.background_music_volume)
                                .attr("textSize", "20sp")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("paddingTop", "10dp")
                        )
                        .addView(
                            SeekBar(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "backgroundVolumeSlider")
                                .attr("layout_height", "20dp")
                                .attr("thumbTint", color.colorPrimaryDark)
                                .attr("progressTint", color.colorPrimaryDark)
                        )
                        .addView(
                            androidx.appcompat.widget.SwitchCompat(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "craftingCountsSwitch")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.show_unlock_counter)
                                .attr("textSize", "20sp")
                                .attr("padding", "10dp")
                                .attr("layout_marginTop", "10dp")
                                .attr("theme", style.SwitchColors)
                                .attr("checked", "true")
                                .attr("textColor", color.colorPrimaryText)
                        )
                        .addView(
                            androidx.appcompat.widget.SwitchCompat(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "displayUUIDSwitch")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.show_element_uuids)
                                .attr("textSize", "20sp")
                                .attr("padding", "10dp")
                                .attr("layout_marginTop", "10dp")
                                .attr("theme", style.SwitchColors)
                                .attr("checked", "true")
                                .attr("textColor", color.colorPrimaryText)
                        )
                        .addView(
                            androidx.appcompat.widget.SwitchCompat(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "offlineModeSwitch")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.offline_mode)
                                .attr("textSize", "20sp")
                                .attr("padding", "10dp")
                                .attr("layout_marginTop", "10dp")
                                .attr("theme", style.SwitchColors)
                                .attr("checked", "false")
                                .attr("textColor", color.colorPrimaryText)
                        )
                        .addView(
                            Button(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "clearRecipeCache")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.clear_recipe_cache)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "saveProgress")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.save_progress)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "loadProgress")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.load_progress)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorPrimary)
                                .attr("id", "resetEverythingButton")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.reset_everything)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("background", color.colorGreen2)
                                .attr("id", "back2")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.back)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorGreen)
                                .attr("fontFamily", "sans-serif")
                        )
                )
        }

        val show_password: View by lazy {
            LinearLayout(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "wrap_content")
                .attr("padding", "10dp")
                .attr("orientation", "vertical")
                .attr("layout_id", "show_password")
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "vertical")
                        .addView(
                            TextView(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "title")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.this_is_your_password)
                                .attr("textSize", "24sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            TextView(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "password")
                                .attr("layout_height", "wrap_content")
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "ok")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.ok)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                        )
                )
        }

        val switch_server: View by lazy {
            ScrollView(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "wrap_content")
                .attr("padding", "10dp")
                .attr("layout_id", "switch_server")
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "vertical")
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "switchToDefault")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.switch_to_default)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            FrameLayout(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .attr("layout_marginTop", "10dp")
                                .addView(
                                    LinearLayout(all, null)
                                        .attr("background", color.colorPrimaryDark)
                                        .attr("layout_width", "match_parent")
                                        .attr("layout_height", "wrap_content")
                                        .attr("padding", "5dp")
                                        .attr("orientation", "vertical")
                                        .addView(
                                            FrameLayout(all, null)
                                                .attr("background", color.colorPrimary)
                                                .attr("layout_width", "match_parent")
                                                .attr("layout_height", "wrap_content")
                                                .addView(
                                                    EditText(all, null)
                                                        .attr("layout_width", "match_parent")
                                                        .attr("id", "name")
                                                        .attr("inputType", "text")
                                                        .attr("layout_height", "wrap_content")
                                                        .attr("padding", "15dp")
                                                        .attr("hint", string.server_name)
                                                )
                                        )
                                        .addView(
                                            FrameLayout(all, null)
                                                .attr("background", color.colorPrimary)
                                                .attr("layout_width", "match_parent")
                                                .attr("layout_height", "wrap_content")
                                                .attr("layout_marginTop", "5dp")
                                                .addView(
                                                    EditText(all, null)
                                                        .attr("layout_width", "match_parent")
                                                        .attr("id", "password")
                                                        .attr("inputType", "textPassword")
                                                        .attr("layout_height", "wrap_content")
                                                        .attr("padding", "15dp")
                                                        .attr("hint", string.password)
                                                )
                                        )
                                        .addView(
                                            Button(all, null)
                                                .attr("background", color.colorPrimary)
                                                .attr("layout_width", "match_parent")
                                                .attr("id", "submit")
                                                .attr("layout_height", "wrap_content")
                                                .attr("text", string.join_that_server)
                                                .attr("textSize", "30sp")
                                                .attr("padding", "10dp")
                                                .attr("textAllCaps", "false")
                                                .attr("layout_marginTop", "5dp")
                                                .attr("gravity", "center")
                                                .attr("textColor", color.colorPrimaryText)
                                                .attr("fontFamily", "sans-serif")
                                        )
                                )
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "createServer")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.create_your_own_server)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "back")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.cancel)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                        )
                )
        }

        val switch_server_create: View by lazy {
            ScrollView(all, null)
                .attr("background", color.colorPrimaryDark)
                .attr("layout_width", "match_parent")
                .attr("layout_height", "wrap_content")
                .attr("padding", "10dp")
                .attr("layout_id", "switch_server_create")
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "vertical")
                        .addView(
                            TextView(all, null)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.server_creation_message)
                                .attr("textSize", "20sp")
                                .attr("padding", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimary)
                        )
                        .addView(
                            FrameLayout(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .attr("layout_marginTop", "10dp")
                                .addView(
                                    EditText(all, null)
                                        .attr("layout_width", "match_parent")
                                        .attr("id", "name")
                                        .attr("inputType", "text")
                                        .attr("layout_height", "wrap_content")
                                        .attr("padding", "15dp")
                                        .attr("hint", string.server_name)
                                )
                        )
                        .addView(
                            FrameLayout(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("layout_height", "wrap_content")
                                .attr("layout_marginTop", "5dp")
                                .addView(
                                    EditText(all, null)
                                        .attr("layout_width", "match_parent")
                                        .attr("id", "password")
                                        .attr("inputType", "textPassword")
                                        .attr("layout_height", "wrap_content")
                                        .attr("padding", "15dp")
                                        .attr("hint", string.password)
                                )
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorGreen2)
                                .attr("layout_width", "match_parent")
                                .attr("id", "createServer")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.create_your_own_server2)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "5dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorGreen)
                                .attr("fontFamily", "sans-serif")
                        )
                        .addView(
                            Button(all, null)
                                .attr("background", color.colorPrimary)
                                .attr("layout_width", "match_parent")
                                .attr("id", "back")
                                .attr("layout_height", "wrap_content")
                                .attr("text", string.cancel)
                                .attr("textSize", "30sp")
                                .attr("padding", "10dp")
                                .attr("textAllCaps", "false")
                                .attr("layout_marginTop", "10dp")
                                .attr("gravity", "center")
                                .attr("textColor", color.colorPrimaryText)
                                .attr("fontFamily", "sans-serif")
                        )
                )
        }

        val tree: View by lazy {
            LinearLayout(all, null)
                .attr("layout_width", "match_parent")
                .attr("background", color.colorPrimaryDark)
                .attr("id", "treeLayout")
                .attr("layout_height", "match_parent")
                .attr("orientation", "vertical")
                .attr("layout_id", "tree")
                .addView(
                    me.antonio.noack.elementalcommunity.LoadingBarView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("layout_height", "5dp")
                )
                .addView(
                    LinearLayout(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("background", color.colorPrimary)
                        .attr("layout_height", "wrap_content")
                        .attr("orientation", "horizontal")
                        .addView(
                            ImageView(all, null)
                                .attr("layout_width", "50sp")
                                .attr("id", "backArrow3")
                                .attr("layout_height", "50sp")
                                .attr("contentDescription", string.back)
                                .attr("src", drawable.ic_arrow_back_black_24dp)
                        )
                        .addView(
                            SeekBar(all, null)
                                .attr("layout_width", "0dp")
                                .attr("id", "spaceSlider")
                                .attr("layout_height", "50dp")
                                .attr("layout_weight", "1")
                                .attr("thumbTint", color.colorPrimaryDark)
                                .attr("progressTint", color.colorPrimaryDark)
                        )
                        .addView(diamond_bar)
                )
                .addView(
                    me.antonio.noack.elementalcommunity.tree.TreeView(all, null)
                        .attr("layout_width", "match_parent")
                        .attr("id", "tree")
                        .attr("layout_height", "match_parent")
                )
        }

        val allLayouts: List<View> by lazy {
            listOf(
                add_recipe, add_recipe_base, all_pages, ask_override, ask_password, combiner,
                diamond_bar, game, graph, helper_offer, helper_search_recipe, itempedia,
                itempedia_item, itempedia_page, mandala, menu, menu_content, progress_load,
                progress_save, random, recipe_helper, select_file, select_folder, settings,
                show_password, switch_server, switch_server_create, tree,
            )
        }

    }

    object id {

        const val appZoom = "appZoom"
        const val back = "back"
        const val back1 = "back1"
        const val back2 = "back2"
        const val back3 = "back3"
        const val backArrow1 = "backArrow1"
        const val backArrow2 = "backArrow2"
        const val backArrow3 = "backArrow3"
        const val backArrow4 = "backArrow4"
        const val backArrow5 = "backArrow5"
        const val backArrow6 = "backArrow6"
        const val backgroundVolumeSlider = "backgroundVolumeSlider"
        const val backgroundVolumeTitle = "backgroundVolumeTitle"
        const val cancel = "cancel"
        const val clearRecipeCache = "clearRecipeCache"
        const val colors = "colors"
        const val combiner = "combiner"
        const val combinerLayout = "combinerLayout"
        const val copy = "copy"
        const val cost = "cost"
        const val craftingCount = "craftingCount"
        const val craftingCountAsIngredient = "craftingCountAsIngredient"
        const val craftingCountsSwitch = "craftingCountsSwitch"
        const val createServer = "createServer"
        const val creationDate = "creationDate"
        const val diamonds = "diamonds"
        const val displayUUIDSwitch = "displayUUIDSwitch"
        const val download = "download"
        const val elementView = "elementView"
        const val favSlider = "favSlider"
        const val favTitle = "favTitle"
        const val fileList = "fileList"
        const val first = "first"
        const val flipper = "flipper"
        const val frequencySlider = "frequencySlider"
        const val frequencyTitle = "frequencyTitle"
        const val gameLayout = "gameLayout"
        const val graph = "graph"
        const val graphButton = "graphButton"
        const val graphLayout = "graphLayout"
        const val itempedia = "itempedia"
        const val itempediaButton = "itempediaButton"
        const val itempediaElements = "itempediaElements"
        const val load = "load"
        const val loadProgress = "loadProgress"
        const val mandalaButton = "mandalaButton"
        const val mandalaLayout = "mandalaLayout"
        const val menuLayout = "menuLayout"
        const val merge = "merge"
        const val name = "name"
        const val newsView = "newsView"
        const val next = "next"
        const val numIngredients = "numIngredients"
        const val numRecipes = "numRecipes"
        const val numSuggestedIngredients = "numSuggestedIngredients"
        const val numSuggestedRecipes = "numSuggestedRecipes"
        const val offers = "offers"
        const val offlineModeSwitch = "offlineModeSwitch"
        const val ok = "ok"
        const val page1 = "page1"
        const val pageFlipper = "pageFlipper"
        const val password = "password"
        const val path = "path"
        const val previews = "previews"
        const val randomButton = "randomButton"
        const val resetEverythingButton = "resetEverythingButton"
        const val save = "save"
        const val saveProgress = "saveProgress"
        const val search = "search"
        const val search1 = "search1"
        const val search2 = "search2"
        const val searchButton1 = "searchButton1"
        const val searchButton2 = "searchButton2"
        const val second = "second"
        const val select = "select"
        const val settingsButton = "settingsButton"
        const val settingsLayout = "settingsLayout"
        const val spaceSlider = "spaceSlider"
        const val start = "start"
        const val submit = "submit"
        const val suggest = "suggest"
        const val suggestions = "suggestions"
        const val switchServer = "switchServer"
        const val switchToDefault = "switchToDefault"
        const val title = "title"
        const val title2 = "title2"
        const val tree = "tree"
        const val tree2 = "tree2"
        const val treeButton = "treeButton"
        const val treeLayout = "treeLayout"
        const val unlocked = "unlocked"
        const val upload = "upload"
        const val uuid = "uuid"

    }

    object raw {

        const val click = "raw/click.mp3"
        const val magic = "raw/magic.mp3"
        const val music_aquatic_omniverse = "raw/music_aquatic_omniverse.mp3"
        const val music_clouds_make2 = "raw/music_clouds_make2.mp3"
        const val music_infinite_elements = "raw/music_infinite_elements.mp3"
        const val ok = "raw/ok.wav"

    }

    val allStyles =
        LinearLayout(all, null)
            .addView(
                View(all, null)
                    .attr("colorPrimary", color.colorPrimary)
                    .attr("colorAccent", color.colorAccent)
                    .attr("parent", "Theme.AppCompat.Light.NoActionBar")
                    .attr("colorPrimaryDark", color.colorPrimaryDark)
                    .attr("id", "AppTheme")
            )
            .addView(
                View(all, null)
                    .attr("colorControlActivated", color.colorPrimaryDark)
                    .attr("parent", "Theme.AppCompat.Light.NoActionBar")
                    .attr("colorSwitchThumbNormal", color.colorPrimaryText)
                    .attr("colorForeground", 0x42221f1f)
                    .attr("id", "SwitchColors")
            )

    object style {

        const val AppTheme = "AppTheme"
        const val SwitchColors = "SwitchColors"

    }

}
// 0.8542499969608806
