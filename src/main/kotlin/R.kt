import android.view.*
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
		const val server_creation_message = "Creating your own server allows you to have different recipes than on the default server. It might be useful for your own community, like your YouTube channel."
		const val show_element_uuids = "Display element ids"
		const val clear_recipe_cache = "Clear Recipe Cache"
		const val mandala_view = "Mandala View"
		const val search = "Search"
		const val what_you_are_searching = "What you are searching"
		const val offline_mode = "Offline Mode"
		const val background_music_volume = "Background Music Volume: #percent"
		const val itempedia = "Itempedia"
		const val close = "Close"
		const val crafting_count_disclaimer = "*Crafting count has been an estimation for many years now, as it isn't tracked 1:1, but more like 1:30."
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
		const val ic_arrow_back_black_24dp = "<svg width=\"24px\" height=\"24px\" viewportWidth=\"24.0\" viewportHeight=\"24.0\"> <path fill=\"rgba(0, 0, 0, 1.0)\" d=\"M20,11H7.83l5.59,-5.59L12,4l-8,8 8,8 1.41,-1.41L7.83,13H20v-2z\"/></svg>"
		const val ic_search_black_24dp = "<svg width=\"24px\" height=\"24px\" viewportWidth=\"24.0\" viewportHeight=\"24.0\"> <path fill=\"rgba(0, 0, 0, 1.0)\" d=\"M15.5,14h-0.79l-0.28,-0.27C15.41,12.59 16,11.11 16,9.5 16,5.91 13.09,3 9.5,3S3,5.91 3,9.5 5.91,16 9.5,16c1.61,0 3.09,-0.59 4.23,-1.57l0.27,0.28v0.79l5,4.99L20.49,19l-4.99,-5zM9.5,14C7.01,14 5,11.99 5,9.5S7.01,5 9.5,5 14,7.01 14,9.5 11.99,14 9.5,14z\"/></svg>"
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
								.attr("gravity", "center")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("padding", "5dp")
								.attr("text", string.result_unknown_wanna_add_your_own)
								.attr("textSize", "20sp")
						)
						.addView(
							TextView(all, null)
								.attr("background", color.colorPrimary)
								.attr("gravity", "center")
								.attr("id", "title2")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("padding", "5dp")
								.attr("text", string.community_suggestions)
								.attr("textSize", "17sp")
						)
						.addView(
							HorizontalScrollView(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.addView(
									LinearLayout(all, null)
										.attr("id", "suggestions")
										.attr("layout_height", "wrap_content")
										.attr("layout_width", "wrap_content")
										.attr("orientation", "horizontal")
								)
						)
						.addView(
							EditText(all, null)
								.attr("hint", string.result_name)
								.attr("id", "name")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("padding", "15dp")
						)
						.addView(
							me.antonio.noack.elementalcommunity.GroupSelectorView(all, null)
								.attr("id", "colors")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
						)
						.addView(
							LinearLayout(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_margin", "5dp")
								.attr("layout_width", "match_parent")
								.attr("orientation", "horizontal")
								.addView(
									Button(all, null)
										.attr("background", color.colorPrimary)
										.attr("id", "submit")
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.submit)
								)
								.addView(
									View(all, null)
										.attr("layout_width", "5dp")
										.attr("layout_height", "5dp")
								)
								.addView(
									Button(all, null)
										.attr("background", color.colorPrimary)
										.attr("id", "cancel")
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.cancel)
								)
						)
				)
		}

		val add_recipe_base: View by lazy {
			ScrollView(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("layout_height", "match_parent")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("layout_id", "add_recipe_base")
				.addView(
					LinearLayout(all, null)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "vertical")
						.addView(
							EditText(all, null)
								.attr("hint", string.result_name)
								.attr("id", "name")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("padding", "15dp")
						)
						.addView(
							me.antonio.noack.elementalcommunity.GroupSelectorView(all, null)
								.attr("id", "colors")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
						)
				)
		}

		val all_pages: View by lazy {
			ViewFlipper(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("id", "flipper")
				.attr("layout_height", "match_parent")
				.attr("layout_width", "match_parent")
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
				.attr("layout_height", "wrap_content")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("padding", "10dp")
				.attr("layout_id", "ask_override")
				.addView(
					Button(all, null)
						.attr("background", color.colorPrimary)
						.attr("fontFamily", "sans-serif")
						.attr("gravity", "center")
						.attr("id", "copy")
						.attr("layout_height", "wrap_content")
						.attr("layout_marginTop", "10dp")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.override_old_data)
						.attr("textAllCaps", "false")
						.attr("textColor", color.colorPrimaryText)
						.attr("textSize", "30sp")
				)
				.addView(
					Button(all, null)
						.attr("background", color.colorPrimary)
						.attr("fontFamily", "sans-serif")
						.attr("gravity", "center")
						.attr("id", "merge")
						.attr("layout_height", "wrap_content")
						.attr("layout_marginTop", "10dp")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.merge_achievements)
						.attr("textAllCaps", "false")
						.attr("textColor", color.colorPrimaryText)
						.attr("textSize", "30sp")
				)
				.addView(
					Button(all, null)
						.attr("background", color.colorPrimary)
						.attr("fontFamily", "sans-serif")
						.attr("gravity", "center")
						.attr("id", "back")
						.attr("layout_height", "wrap_content")
						.attr("layout_marginTop", "10dp")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.cancel)
						.attr("textAllCaps", "false")
						.attr("textColor", color.colorPrimaryText)
						.attr("textSize", "30sp")
				)
		}

		val ask_password: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("layout_height", "wrap_content")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("padding", "10dp")
				.attr("layout_id", "ask_password")
				.addView(
					FrameLayout(all, null)
						.attr("background", color.colorPrimary)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "vertical")
						.addView(
							EditText(all, null)
								.attr("hint", string.please_enter_the_password)
								.attr("id", "password")
								.attr("inputType", "numberPassword")
								.attr("layout_height", "wrap_content")
								.attr("layout_margin", "5dp")
								.attr("layout_width", "match_parent")
								.attr("textSize", "24sp")
						)
				)
				.addView(
					LinearLayout(all, null)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "horizontal")
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "back")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_weight", "1")
								.attr("layout_width", "0dp")
								.attr("padding", "10dp")
								.attr("text", string.cancel)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "30sp")
						)
						.addView(
							View(all, null)
								.attr("layout_height", "1dp")
								.attr("layout_width", "10dp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "ok")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_weight", "1")
								.attr("layout_width", "0dp")
								.attr("padding", "10dp")
								.attr("text", string.ok)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "30sp")
						)
				)
		}

		val combiner: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("id", "combinerLayout")
				.attr("layout_height", "match_parent")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("layout_id", "combiner")
				.addView(
					me.antonio.noack.elementalcommunity.LoadingBarView(all, null)
						.attr("layout_height", "5dp")
						.attr("layout_width", "match_parent")
				)
				.addView(
					LinearLayout(all, null)
						.attr("background", color.colorPrimary)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "horizontal")
						.addView(
							ImageView(all, null)
								.attr("contentDescription", string.back)
								.attr("id", "backArrow2")
								.attr("layout_height", "50sp")
								.attr("layout_width", "50sp")
								.attr("src", drawable.ic_arrow_back_black_24dp)
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "back1")
								.attr("layout_height", "50sp")
								.attr("layout_weight", "1")
								.attr("layout_width", "0dp")
								.attr("padding", "5dp")
								.attr("text", string.back)
								.attr("textAllCaps", "false")
								.attr("textSize", "24sp")
						)
						.addView(
							ImageView(all, null)
								.attr("contentDescription", string.search)
								.attr("id", "searchButton2")
								.attr("layout_height", "50sp")
								.attr("layout_width", "50sp")
								.attr("src", drawable.ic_search_black_24dp)
						)
						.addView(
							EditText(all, null)
								.attr("hint", string.what_you_are_searching)
								.attr("id", "search2")
								.attr("importantForAutofill", "no")
								.attr("inputType", "text")
								.attr("layout_height", "50sp")
								.attr("layout_weight", "3")
								.attr("layout_width", "0dp")
								.attr("maxLines", "1")
								.attr("visibility", "gone")
						)
						.addView(diamond_bar)
				)
				.addView(
					me.antonio.noack.elementalcommunity.Combiner(all, null)
						.attr("id", "combiner")
						.attr("layout_height", "match_parent")
						.attr("layout_width", "match_parent")
				)
		}

		val diamond_bar: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorDiamond)
				.attr("layout_height", "wrap_content")
				.attr("layout_width", "wrap_content")
				.attr("orientation", "horizontal")
				.attr("layout_id", "diamond_bar")
				.addView(
					View(all, null)
						.attr("background", color.colorPrimaryDark)
						.attr("layout_height", "50sp")
						.attr("layout_width", "3dp")
				)
				.addView(
					ImageView(all, null)
						.attr("layout_height", "50sp")
						.attr("layout_width", "50sp")
						.attr("src", drawable.diamond_icon)
				)
				.addView(
					TextView(all, null)
						.attr("gravity", "center")
						.attr("id", "diamonds")
						.attr("layout_height", "50sp")
						.attr("layout_width", "wrap_content")
						.attr("paddingBottom", "5dp")
						.attr("paddingEnd", "5dp")
						.attr("paddingLeft", "0dp")
						.attr("paddingRight", "5dp")
						.attr("paddingStart", "0dp")
						.attr("paddingTop", "5dp")
						.attr("text", string.diamond_example)
						.attr("textColor", color.colorDiamond2)
						.attr("textSize", "24sp")
				)
		}

		val game: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("id", "gameLayout")
				.attr("layout_height", "match_parent")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("layout_id", "game")
				.addView(
					me.antonio.noack.elementalcommunity.LoadingBarView(all, null)
						.attr("layout_height", "5dp")
						.attr("layout_width", "match_parent")
				)
				.addView(
					LinearLayout(all, null)
						.attr("background", color.colorPrimary)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "horizontal")
						.addView(
							ImageView(all, null)
								.attr("contentDescription", string.back)
								.attr("id", "backArrow1")
								.attr("layout_height", "50sp")
								.attr("layout_width", "50sp")
								.attr("src", drawable.ic_arrow_back_black_24dp)
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "back3")
								.attr("layout_height", "50sp")
								.attr("layout_weight", "1")
								.attr("layout_width", "0dp")
								.attr("padding", "5dp")
								.attr("text", string.back)
								.attr("textAllCaps", "false")
								.attr("textSize", "24sp")
						)
						.addView(
							ImageView(all, null)
								.attr("id", "searchButton1")
								.attr("layout_height", "50sp")
								.attr("layout_width", "50sp")
								.attr("src", drawable.ic_search_black_24dp)
						)
						.addView(
							EditText(all, null)
								.attr("hint", string.what_you_are_searching)
								.attr("id", "search1")
								.attr("importantForAutofill", "no")
								.attr("inputType", "text")
								.attr("layout_height", "50sp")
								.attr("layout_weight", "3")
								.attr("layout_width", "0dp")
								.attr("maxLines", "1")
								.attr("visibility", "gone")
						)
						.addView(diamond_bar)
				)
				.addView(
					me.antonio.noack.elementalcommunity.UnlockedRows(all, null)
						.attr("id", "unlocked")
						.attr("layout_height", "match_parent")
						.attr("layout_width", "match_parent")
				)
		}

		val graph: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("id", "graphLayout")
				.attr("layout_height", "match_parent")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("layout_id", "graph")
				.addView(
					me.antonio.noack.elementalcommunity.LoadingBarView(all, null)
						.attr("layout_height", "5dp")
						.attr("layout_width", "match_parent")
				)
				.addView(
					LinearLayout(all, null)
						.attr("background", color.colorPrimary)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "horizontal")
						.addView(
							ImageView(all, null)
								.attr("contentDescription", string.back)
								.attr("id", "backArrow5")
								.attr("layout_height", "50sp")
								.attr("layout_width", "50sp")
								.attr("src", drawable.ic_arrow_back_black_24dp)
						)
						.addView(
							Space(all, null)
								.attr("layout_height", "0dp")
								.attr("layout_weight", "1")
								.attr("layout_width", "0dp")
						)
						.addView(diamond_bar)
				)
				.addView(
					me.antonio.noack.elementalcommunity.graph.GraphView(all, null)
						.attr("id", "graph")
						.attr("layout_height", "match_parent")
						.attr("layout_width", "match_parent")
				)
		}

		val helper_offer: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorDiamond)
				.attr("layout_height", "wrap_content")
				.attr("layout_marginTop", "5dp")
				.attr("layout_width", "match_parent")
				.attr("orientation", "horizontal")
				.attr("padding", "5dp")
				.attr("layout_id", "helper_offer")
				.addView(
					View(all, null)
						.attr("layout_height", "1dp")
						.attr("layout_weight", "1")
						.attr("layout_width", "0dp")
				)
				.addView(
					TextView(all, null)
						.attr("id", "title")
						.attr("layout_height", "30sp")
						.attr("layout_width", "wrap_content")
						.attr("padding", "5dp")
						.attr("text", string.recipe_lookup)
						.attr("textColor", color.colorDiamond2)
						.attr("textSize", "15sp")
				)
				.addView(
					View(all, null)
						.attr("layout_height", "1dp")
						.attr("layout_width", "5dp")
				)
				.addView(
					LinearLayout(all, null)
						.attr("layout_height", "30sp")
						.attr("layout_width", "wrap_content")
						.attr("orientation", "horizontal")
						.attr("paddingLeft", "5dp")
						.attr("paddingRight", "5dp")
						.addView(
							ImageView(all, null)
								.attr("layout_height", "30sp")
								.attr("layout_width", "30sp")
								.attr("src", drawable.diamond_icon)
						)
						.addView(
							TextView(all, null)
								.attr("id", "cost")
								.attr("layout_height", "30sp")
								.attr("layout_width", "wrap_content")
								.attr("padding", "5sp")
								.attr("text", string.lookup_cost)
								.attr("textColor", color.colorDiamond2)
								.attr("textSize", "15sp")
						)
				)
				.addView(
					View(all, null)
						.attr("layout_height", "1dp")
						.attr("layout_weight", "1")
						.attr("layout_width", "0dp")
				)
		}

		val helper_search_recipe: View by lazy {
			LinearLayout(all, null)
				.attr("layout_height", "wrap_content")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("layout_id", "helper_search_recipe")
				.addView(
					LinearLayout(all, null)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "horizontal")
						.addView(
							ImageView(all, null)
								.attr("layout_height", "50sp")
								.attr("layout_width", "50sp")
								.attr("src", drawable.ic_search_black_24dp)
						)
						.addView(
							EditText(all, null)
								.attr("hint", string.result_name)
								.attr("id", "search")
								.attr("layout_height", "50sp")
								.attr("layout_width", "match_parent")
								.attr("maxLines", "1")
						)
				)
				.addView(
					LinearLayout(all, null)
						.attr("background", color.colorPrimaryDark)
						.attr("id", "previews")
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "vertical")
						.attr("padding", "10dp")
						.attr("visibility", "gone")
				)
		}

		val itempedia: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorPrimaryText)
				.attr("id", "itempedia")
				.attr("layout_height", "match_parent")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("layout_id", "itempedia")
				.addView(
					me.antonio.noack.elementalcommunity.LoadingBarView(all, null)
						.attr("layout_height", "5dp")
						.attr("layout_width", "match_parent")
				)
				.addView(
					LinearLayout(all, null)
						.attr("background", color.colorPrimary)
						.attr("layout_height", "wrap_content")
						.attr("layout_marginBottom", "5dp")
						.attr("layout_width", "match_parent")
						.attr("orientation", "horizontal")
						.addView(
							ImageView(all, null)
								.attr("contentDescription", string.back)
								.attr("id", "backArrow6")
								.attr("layout_height", "50sp")
								.attr("layout_width", "50sp")
								.attr("src", drawable.ic_arrow_back_black_24dp)
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "itempediaTitle")
								.attr("layout_height", "50sp")
								.attr("layout_weight", "1")
								.attr("layout_width", "0dp")
								.attr("padding", "5dp")
								.attr("text", string.itempedia)
								.attr("textAllCaps", "false")
								.attr("textSize", "24sp")
						)
						.addView(
							ImageView(all, null)
								.attr("id", "searchButton3")
								.attr("layout_height", "50sp")
								.attr("layout_width", "50sp")
								.attr("src", drawable.ic_search_black_24dp)
						)
						.addView(
							EditText(all, null)
								.attr("hint", string.what_you_are_searching)
								.attr("id", "search3")
								.attr("importantForAutofill", "no")
								.attr("inputType", "text")
								.attr("layout_height", "50sp")
								.attr("layout_weight", "3")
								.attr("layout_width", "0dp")
								.attr("maxLines", "1")
								.attr("visibility", "gone")
						)
				)
				.addView(
					androidx.recyclerview.widget.RecyclerView(all, null)
						.attr("id", "itempediaElements")
						.attr("layout_height", "0dp")
						.attr("layout_weight", "1")
						.attr("layout_width", "match_parent")
				)
				.addView(
					HorizontalScrollView(all, null)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.addView(
							LinearLayout(all, null)
								.attr("id", "pageFlipper")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "wrap_content")
								.attr("orientation", "horizontal")
								.attr("paddingLeft", "2dp")
								.attr("paddingRight", "2dp")
								.addView(itempedia_page)
								.addView(itempedia_page)
								.addView(itempedia_page)
						)
				)
		}

		val itempedia_item: View by lazy {
			ScrollView(all, null)
				.attr("layout_height", "wrap_content")
				.attr("layout_width", "match_parent")
				.attr("layout_id", "itempedia_item")
				.addView(
					LinearLayout(all, null)
						.attr("background", color.colorPrimaryText)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "vertical")
						.addView(
							TextView(all, null)
								.attr("gravity", "center")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.itempedia_stats)
								.attr("textColor", 0x7f6fffff)
								.attr("textSize", "20sp")
						)
						.addView(
							me.antonio.noack.elementalcommunity.OneElement(all, null)
								.attr("id", "elementView")
								.attr("layout_gravity", "center")
								.attr("layout_height", "200dp")
								.attr("layout_width", "200dp")
						)
						.addView(
							LinearLayout(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("orientation", "horizontal")
								.attr("paddingLeft", "10dp")
								.attr("paddingRight", "10dp")
								.addView(
									TextView(all, null)
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.unique_identifier)
										.attr("textColor", 0xab9fffff.toInt())
										.attr("textSize", "20sp")
								)
								.addView(
									TextView(all, null)
										.attr("gravity", "end")
										.attr("id", "uuid")
										.attr("layout_height", "wrap_content")
										.attr("layout_width", "wrap_content")
										.attr("text", "")
										.attr("textColor", 0xffffffff.toInt())
										.attr("textSize", "20sp")
								)
						)
						.addView(
							LinearLayout(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("orientation", "horizontal")
								.attr("paddingLeft", "10dp")
								.attr("paddingRight", "10dp")
								.addView(
									TextView(all, null)
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.creation_date_title)
										.attr("textColor", 0xab9fffff.toInt())
										.attr("textSize", "20sp")
								)
								.addView(
									TextView(all, null)
										.attr("gravity", "end")
										.attr("id", "creationDate")
										.attr("layout_height", "wrap_content")
										.attr("layout_width", "wrap_content")
										.attr("text", "")
										.attr("textColor", 0xffffffff.toInt())
										.attr("textSize", "20sp")
								)
						)
						.addView(
							View(all, null)
								.attr("background", 0x33300)
								.attr("layout_height", "1dp")
								.attr("layout_margin", "5dp")
								.attr("layout_width", "match_parent")
						)
						.addView(
							LinearLayout(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("orientation", "horizontal")
								.attr("paddingLeft", "10dp")
								.attr("paddingRight", "10dp")
								.addView(
									TextView(all, null)
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.crafting_count_title)
										.attr("textColor", 0xab9fffff.toInt())
										.attr("textSize", "20sp")
								)
								.addView(
									TextView(all, null)
										.attr("gravity", "end")
										.attr("id", "craftingCount")
										.attr("layout_height", "wrap_content")
										.attr("layout_width", "wrap_content")
										.attr("text", "")
										.attr("textColor", 0xffffffff.toInt())
										.attr("textSize", "20sp")
								)
						)
						.addView(
							LinearLayout(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("orientation", "horizontal")
								.attr("paddingLeft", "10dp")
								.attr("paddingRight", "10dp")
								.addView(
									TextView(all, null)
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.crafting_count_as_component)
										.attr("textColor", 0xab9fffff.toInt())
										.attr("textSize", "20sp")
								)
								.addView(
									TextView(all, null)
										.attr("gravity", "end")
										.attr("id", "craftingCountAsIngredient")
										.attr("layout_height", "wrap_content")
										.attr("layout_width", "wrap_content")
										.attr("text", "")
										.attr("textColor", 0xffffffff.toInt())
										.attr("textSize", "20sp")
								)
						)
						.addView(
							View(all, null)
								.attr("background", 0x33300)
								.attr("layout_height", "1dp")
								.attr("layout_margin", "5dp")
								.attr("layout_width", "match_parent")
						)
						.addView(
							LinearLayout(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("orientation", "horizontal")
								.attr("paddingLeft", "10dp")
								.attr("paddingRight", "10dp")
								.addView(
									TextView(all, null)
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.number_of_recipes)
										.attr("textColor", 0xab9fffff.toInt())
										.attr("textSize", "20sp")
								)
								.addView(
									TextView(all, null)
										.attr("gravity", "end")
										.attr("id", "numRecipes")
										.attr("layout_height", "wrap_content")
										.attr("layout_width", "wrap_content")
										.attr("text", "")
										.attr("textColor", 0xffffffff.toInt())
										.attr("textSize", "20sp")
								)
						)
						.addView(
							LinearLayout(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("orientation", "horizontal")
								.attr("paddingLeft", "10dp")
								.attr("paddingRight", "10dp")
								.addView(
									TextView(all, null)
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.times_used_as_component)
										.attr("textColor", 0xab9fffff.toInt())
										.attr("textSize", "20sp")
								)
								.addView(
									TextView(all, null)
										.attr("gravity", "end")
										.attr("id", "numIngredients")
										.attr("layout_height", "wrap_content")
										.attr("layout_width", "wrap_content")
										.attr("text", "")
										.attr("textColor", 0xffffffff.toInt())
										.attr("textSize", "20sp")
								)
						)
						.addView(
							View(all, null)
								.attr("background", 0x33300)
								.attr("layout_height", "1dp")
								.attr("layout_margin", "5dp")
								.attr("layout_width", "match_parent")
						)
						.addView(
							LinearLayout(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("orientation", "horizontal")
								.attr("paddingLeft", "10dp")
								.attr("paddingRight", "10dp")
								.addView(
									TextView(all, null)
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.suggestions_for_recipes)
										.attr("textColor", 0xab9fffff.toInt())
										.attr("textSize", "20sp")
								)
								.addView(
									TextView(all, null)
										.attr("gravity", "end")
										.attr("id", "numSuggestedRecipes")
										.attr("layout_height", "wrap_content")
										.attr("layout_width", "wrap_content")
										.attr("text", "")
										.attr("textColor", 0xffffffff.toInt())
										.attr("textSize", "20sp")
								)
						)
						.addView(
							LinearLayout(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("orientation", "horizontal")
								.attr("paddingLeft", "10dp")
								.attr("paddingRight", "10dp")
								.addView(
									TextView(all, null)
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.times_suggested_as_component)
										.attr("textColor", 0xab9fffff.toInt())
										.attr("textSize", "20sp")
								)
								.addView(
									TextView(all, null)
										.attr("gravity", "end")
										.attr("id", "numSuggestedIngredients")
										.attr("layout_height", "wrap_content")
										.attr("layout_width", "wrap_content")
										.attr("text", "")
										.attr("textColor", 0xffffffff.toInt())
										.attr("textSize", "20sp")
								)
						)
						.addView(
							TextView(all, null)
								.attr("gravity", "center")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.crafting_count_disclaimer)
								.attr("textColor", 0x897fffff.toInt())
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "back")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "3dp")
								.attr("text", string.close)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "20sp")
						)
				)
		}

		val itempedia_page: View by lazy {
			TextView(all, null)
				.attr("background", color.colorPrimary)
				.attr("gravity", "center")
				.attr("id", "page1")
				.attr("layout_height", "50dp")
				.attr("layout_marginBottom", "4dp")
				.attr("layout_marginLeft", "2dp")
				.attr("layout_marginRight", "2dp")
				.attr("layout_marginTop", "4dp")
				.attr("layout_width", "50dp")
				.attr("text", "423")
				.attr("textColor", 0xffffffff.toInt())
				.attr("textSize", "22sp")
				.attr("layout_id", "itempedia_page")
		}

		val mandala: View by lazy {
			LinearLayout(all, null)
				.attr("id", "mandalaLayout")
				.attr("layout_height", "match_parent")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("layout_id", "mandala")
				.addView(
					me.antonio.noack.elementalcommunity.LoadingBarView(all, null)
						.attr("layout_height", "5dp")
						.attr("layout_width", "match_parent")
				)
				.addView(
					LinearLayout(all, null)
						.attr("background", color.colorPrimary)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "horizontal")
						.addView(
							ImageView(all, null)
								.attr("contentDescription", string.back)
								.attr("id", "backArrow4")
								.attr("layout_height", "50sp")
								.attr("layout_width", "50sp")
								.attr("src", drawable.ic_arrow_back_black_24dp)
						)
						.addView(
							View(all, null)
								.attr("layout_height", "1dp")
								.attr("layout_weight", "1")
								.attr("layout_width", "0dp")
						)
						.addView(diamond_bar)
				)
				.addView(
					me.antonio.noack.elementalcommunity.mandala.MandalaView(all, null)
						.attr("id", "tree2")
						.attr("layout_height", "match_parent")
						.attr("layout_width", "match_parent")
				)
		}

		val menu: View by lazy {
			FrameLayout(all, null)
				.attr("background", 0xffffffff.toInt())
				.attr("id", "menuLayout")
				.attr("layout_height", "match_parent")
				.attr("layout_width", "match_parent")
				.attr("layout_id", "menu")
				.addView(
					me.antonio.noack.elementalcommunity.NewsView(all, null)
						.attr("id", "newsView")
						.attr("layout_height", "match_parent")
						.attr("layout_width", "match_parent")
				)
				.addView(
					LinearLayout(all, null)
						.attr("background", 0x70006096)
						.attr("id", "appZoom")
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "vertical")
						.addView(
							View(all, null)
								.attr("layout_height", "0dp")
								.attr("layout_weight", "1")
								.attr("layout_width", "match_parent")
						)
						.addView(menu_content)
						.addView(
							View(all, null)
								.attr("layout_height", "0dp")
								.attr("layout_weight", "1")
								.attr("layout_width", "match_parent")
						)
				)
		}

		val menu_content: View by lazy {
			ScrollView(all, null)
				.attr("layout_height", "wrap_content")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("layout_id", "menu_content")
				.addView(
					LinearLayout(all, null)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "vertical")
						.addView(
							TextView(all, null)
								.attr("background", color.colorPrimary)
								.attr("gravity", "center")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginBottom", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.app_name)
								.attr("textColor", color.colorAccent)
								.attr("textSize", "30sp")
								.attr("textStyle", "bold")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "start")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.start)
								.attr("textAllCaps", "false")
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "mandalaButton")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.mandala_view)
								.attr("textAllCaps", "false")
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "treeButton")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.tree_view)
								.attr("textAllCaps", "false")
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "graphButton")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", "Graph View")
								.attr("textAllCaps", "false")
								.attr("textSize", "30sp")
								.attr("visibility", "gone")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "itempediaButton")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.itempedia)
								.attr("textAllCaps", "false")
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "randomButton")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.random_suggestion)
								.attr("textAllCaps", "false")
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "suggest")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.suggest)
								.attr("textAllCaps", "false")
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "settingsButton")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.settings)
								.attr("textAllCaps", "false")
								.attr("textSize", "30sp")
						)
				)
		}

		val progress_load: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("layout_height", "wrap_content")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("padding", "10dp")
				.attr("layout_id", "progress_load")
				.addView(
					Button(all, null)
						.attr("background", color.colorPrimary)
						.attr("fontFamily", "sans-serif")
						.attr("gravity", "center")
						.attr("id", "download")
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.download_progress_from_server)
						.attr("textAllCaps", "false")
						.attr("textColor", color.colorPrimaryText)
						.attr("textSize", "30sp")
				)
				.addView(
					Button(all, null)
						.attr("background", color.colorPrimary)
						.attr("fontFamily", "sans-serif")
						.attr("gravity", "center")
						.attr("id", "load")
						.attr("layout_height", "wrap_content")
						.attr("layout_marginTop", "10dp")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.load_progress_from_file)
						.attr("textAllCaps", "false")
						.attr("textColor", color.colorPrimaryText)
						.attr("textSize", "30sp")
				)
				.addView(
					Button(all, null)
						.attr("background", color.colorPrimary)
						.attr("fontFamily", "sans-serif")
						.attr("gravity", "center")
						.attr("id", "back")
						.attr("layout_height", "wrap_content")
						.attr("layout_marginTop", "10dp")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.cancel)
						.attr("textAllCaps", "false")
						.attr("textColor", color.colorPrimaryText)
						.attr("textSize", "30sp")
				)
		}

		val progress_save: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("layout_height", "wrap_content")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("padding", "10dp")
				.attr("layout_id", "progress_save")
				.addView(
					Button(all, null)
						.attr("background", color.colorPrimary)
						.attr("fontFamily", "sans-serif")
						.attr("gravity", "center")
						.attr("id", "upload")
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.upload_progress_to_server)
						.attr("textAllCaps", "false")
						.attr("textColor", color.colorPrimaryText)
						.attr("textSize", "30sp")
				)
				.addView(
					Button(all, null)
						.attr("background", color.colorPrimary)
						.attr("fontFamily", "sans-serif")
						.attr("gravity", "center")
						.attr("id", "save")
						.attr("layout_height", "wrap_content")
						.attr("layout_marginTop", "10dp")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.save_progress_to_file)
						.attr("textAllCaps", "false")
						.attr("textColor", color.colorPrimaryText)
						.attr("textSize", "30sp")
				)
				.addView(
					Button(all, null)
						.attr("background", color.colorPrimary)
						.attr("fontFamily", "sans-serif")
						.attr("gravity", "center")
						.attr("id", "back")
						.attr("layout_height", "wrap_content")
						.attr("layout_marginTop", "10dp")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.cancel)
						.attr("textAllCaps", "false")
						.attr("textColor", color.colorPrimaryText)
						.attr("textSize", "30sp")
				)
		}

		val random: View by lazy {
			ScrollView(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("layout_height", "match_parent")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("layout_id", "random")
				.addView(
					LinearLayout(all, null)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "vertical")
						.addView(
							LinearLayout(all, null)
								.attr("background", color.colorPrimary)
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("orientation", "horizontal")
								.attr("padding", "5dp")
								.addView(
									TextView(all, null)
										.attr("gravity", "center_horizontal")
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.component_a)
										.attr("textSize", "20sp")
								)
								.addView(
									TextView(all, null)
										.attr("gravity", "center_horizontal")
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.component_b)
										.attr("textSize", "20sp")
								)
						)
						.addView(
							LinearLayout(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_margin", "5dp")
								.attr("layout_width", "match_parent")
								.attr("orientation", "horizontal")
								.addView(
									me.antonio.noack.elementalcommunity.OneElement(all, null)
										.attr("id", "first")
										.attr("layout_gravity", "center")
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
								)
								.addView(
									me.antonio.noack.elementalcommunity.OneElement(all, null)
										.attr("id", "second")
										.attr("layout_gravity", "center")
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
								)
						)
						.addView(
							EditText(all, null)
								.attr("hint", string.result_name)
								.attr("id", "name")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("padding", "15dp")
						)
						.addView(
							me.antonio.noack.elementalcommunity.GroupSelectorView(all, null)
								.attr("id", "colors")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
						)
						.addView(
							LinearLayout(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_margin", "5dp")
								.attr("layout_width", "match_parent")
								.attr("orientation", "horizontal")
								.addView(
									Button(all, null)
										.attr("background", color.colorPrimary)
										.attr("id", "submit")
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.submit)
								)
								.addView(
									View(all, null)
										.attr("layout_height", "5dp")
										.attr("layout_width", "5dp")
								)
								.addView(
									Button(all, null)
										.attr("background", color.colorPrimary)
										.attr("id", "cancel")
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.cancel)
								)
								.addView(
									View(all, null)
										.attr("layout_height", "5dp")
										.attr("layout_width", "5dp")
								)
								.addView(
									Button(all, null)
										.attr("background", color.colorPrimary)
										.attr("id", "next")
										.attr("layout_height", "wrap_content")
										.attr("layout_weight", "1")
										.attr("layout_width", "0dp")
										.attr("text", string.skip_that)
								)
						)
				)
		}

		val recipe_helper: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorPrimary)
				.attr("layout_height", "wrap_content")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("padding", "5dp")
				.attr("layout_id", "recipe_helper")
				.addView(
					LinearLayout(all, null)
						.attr("background", color.colorDiamond)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "horizontal")
						.attr("padding", "5dp")
						.addView(
							View(all, null)
								.attr("layout_height", "1dp")
								.attr("layout_weight", "1")
								.attr("layout_width", "0dp")
						)
						.addView(
							ImageView(all, null)
								.attr("layout_height", "50sp")
								.attr("layout_width", "50sp")
								.attr("src", drawable.diamond_icon)
						)
						.addView(
							TextView(all, null)
								.attr("gravity", "center")
								.attr("id", "diamonds")
								.attr("layout_height", "50sp")
								.attr("layout_width", "wrap_content")
								.attr("padding", "5dp")
								.attr("text", string.diamond_example)
								.attr("textColor", color.colorDiamond2)
								.attr("textSize", "24sp")
						)
						.addView(
							View(all, null)
								.attr("layout_height", "1dp")
								.attr("layout_weight", "1")
								.attr("layout_width", "0dp")
						)
				)
				.addView(
					ScrollView(all, null)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.addView(
							LinearLayout(all, null)
								.attr("id", "offers")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("orientation", "vertical")
						)
				)
		}

		val select_file: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("layout_height", "wrap_content")
				.attr("layout_marginTop", "10dp")
				.attr("layout_width", "wrap_content")
				.attr("orientation", "vertical")
				.attr("layout_id", "select_file")
				.addView(
					TextView(all, null)
						.attr("background", color.colorPrimary)
						.attr("id", "title")
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("padding", "5dp")
						.attr("text", string.please_select_the_file)
						.attr("textSize", "24sp")
				)
		}

		val select_folder: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("layout_height", "match_parent")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("padding", "10dp")
				.attr("layout_id", "select_folder")
				.addView(
					TextView(all, null)
						.attr("background", color.colorPrimary)
						.attr("id", "title")
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.please_select_the_file)
						.attr("textSize", "30sp")
				)
				.addView(
					TextView(all, null)
						.attr("background", color.colorPrimary)
						.attr("id", "path")
						.attr("layout_height", "wrap_content")
						.attr("layout_marginLeft", "10dp")
						.attr("layout_marginRight", "10dp")
						.attr("layout_marginTop", "10dp")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.app_name)
						.attr("textSize", "17sp")
				)
				.addView(
					ScrollView(all, null)
						.attr("layout_height", "0dp")
						.attr("layout_margin", "10dp")
						.attr("layout_marginLeft", "10dp")
						.attr("layout_marginRight", "10dp")
						.attr("layout_weight", "1")
						.attr("layout_width", "match_parent")
						.addView(
							me.antonio.noack.elementalcommunity.PatchesView(all, null)
								.attr("id", "fileList")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("orientation", "vertical")
						)
				)
				.addView(
					Button(all, null)
						.attr("background", color.colorPrimary)
						.attr("fontFamily", "sans-serif")
						.attr("gravity", "center")
						.attr("id", "select")
						.attr("layout_height", "wrap_content")
						.attr("layout_marginBottom", "10dp")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.save_progress_to_name)
						.attr("textAllCaps", "false")
						.attr("textColor", color.colorPrimaryText)
						.attr("textSize", "24sp")
				)
				.addView(
					Button(all, null)
						.attr("background", color.colorPrimary)
						.attr("fontFamily", "sans-serif")
						.attr("gravity", "center")
						.attr("id", "back")
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("padding", "10dp")
						.attr("text", string.cancel)
						.attr("textAllCaps", "false")
						.attr("textColor", color.colorPrimaryText)
						.attr("textSize", "24sp")
				)
		}

		val settings: View by lazy {
			ScrollView(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("id", "settingsLayout")
				.attr("layout_height", "match_parent")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("layout_id", "settings")
				.addView(
					LinearLayout(all, null)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "vertical")
						.addView(
							TextView(all, null)
								.attr("gravity", "center")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "5dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.settings_help)
								.attr("textColor", color.colorPrimary)
								.attr("textSize", "20sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorGreen2)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "switchServer")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.switch_server)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorGreen)
								.attr("textSize", "30sp")
						)
						.addView(
							TextView(all, null)
								.attr("background", color.colorPrimary)
								.attr("gravity", "center")
								.attr("id", "favTitle")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("paddingTop", "10dp")
								.attr("text", string.favourites)
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "20sp")
						)
						.addView(
							SeekBar(all, null)
								.attr("background", color.colorPrimary)
								.attr("id", "favSlider")
								.attr("layout_height", "20dp")
								.attr("layout_width", "match_parent")
								.attr("progressTint", color.colorPrimaryDark)
								.attr("thumbTint", color.colorPrimaryDark)
						)
						.addView(
							TextView(all, null)
								.attr("background", color.colorPrimary)
								.attr("gravity", "center")
								.attr("id", "frequencyTitle")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("paddingTop", "10dp")
								.attr("text", string.frequency_of_asking_title)
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "20sp")
						)
						.addView(
							SeekBar(all, null)
								.attr("background", color.colorPrimary)
								.attr("id", "frequencySlider")
								.attr("layout_height", "20dp")
								.attr("layout_width", "match_parent")
								.attr("progressTint", color.colorPrimaryDark)
								.attr("thumbTint", color.colorPrimaryDark)
						)
						.addView(
							TextView(all, null)
								.attr("background", color.colorPrimary)
								.attr("gravity", "center")
								.attr("id", "backgroundVolumeTitle")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("paddingTop", "10dp")
								.attr("text", string.background_music_volume)
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "20sp")
						)
						.addView(
							SeekBar(all, null)
								.attr("background", color.colorPrimary)
								.attr("id", "backgroundVolumeSlider")
								.attr("layout_height", "20dp")
								.attr("layout_width", "match_parent")
								.attr("progressTint", color.colorPrimaryDark)
								.attr("thumbTint", color.colorPrimaryDark)
						)
						.addView(
							androidx.appcompat.widget.SwitchCompat(all, null)
								.attr("background", color.colorPrimary)
								.attr("checked", "true")
								.attr("id", "craftingCountsSwitch")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.show_unlock_counter)
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "20sp")
								.attr("theme", style.SwitchColors)
						)
						.addView(
							androidx.appcompat.widget.SwitchCompat(all, null)
								.attr("background", color.colorPrimary)
								.attr("checked", "true")
								.attr("id", "displayUUIDSwitch")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.show_element_uuids)
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "20sp")
								.attr("theme", style.SwitchColors)
						)
						.addView(
							androidx.appcompat.widget.SwitchCompat(all, null)
								.attr("background", color.colorPrimary)
								.attr("checked", "false")
								.attr("id", "offlineModeSwitch")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.offline_mode)
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "20sp")
								.attr("theme", style.SwitchColors)
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "clearRecipeCache")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.clear_recipe_cache)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "saveProgress")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.save_progress)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "loadProgress")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.load_progress)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "resetEverythingButton")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.reset_everything)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorGreen2)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "back2")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.back)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorGreen)
								.attr("textSize", "30sp")
						)
				)
		}

		val show_password: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("layout_height", "wrap_content")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("padding", "10dp")
				.attr("layout_id", "show_password")
				.addView(
					LinearLayout(all, null)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "vertical")
						.addView(
							TextView(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "title")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.this_is_your_password)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "24sp")
						)
						.addView(
							TextView(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "password")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "ok")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.ok)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "30sp")
						)
				)
		}

		val switch_server: View by lazy {
			ScrollView(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("layout_height", "wrap_content")
				.attr("layout_width", "match_parent")
				.attr("padding", "10dp")
				.attr("layout_id", "switch_server")
				.addView(
					LinearLayout(all, null)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "vertical")
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "switchToDefault")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.switch_to_default)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "30sp")
						)
						.addView(
							FrameLayout(all, null)
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.addView(
									LinearLayout(all, null)
										.attr("background", color.colorPrimaryDark)
										.attr("layout_height", "wrap_content")
										.attr("layout_width", "match_parent")
										.attr("orientation", "vertical")
										.attr("padding", "5dp")
										.addView(
											FrameLayout(all, null)
												.attr("background", color.colorPrimary)
												.attr("layout_height", "wrap_content")
												.attr("layout_width", "match_parent")
												.addView(
													EditText(all, null)
														.attr("hint", string.server_name)
														.attr("id", "name")
														.attr("inputType", "text")
														.attr("layout_height", "wrap_content")
														.attr("layout_width", "match_parent")
														.attr("padding", "15dp")
												)
										)
										.addView(
											FrameLayout(all, null)
												.attr("background", color.colorPrimary)
												.attr("layout_height", "wrap_content")
												.attr("layout_marginTop", "5dp")
												.attr("layout_width", "match_parent")
												.addView(
													EditText(all, null)
														.attr("hint", string.password)
														.attr("id", "password")
														.attr("inputType", "textPassword")
														.attr("layout_height", "wrap_content")
														.attr("layout_width", "match_parent")
														.attr("padding", "15dp")
												)
										)
										.addView(
											Button(all, null)
												.attr("background", color.colorPrimary)
												.attr("fontFamily", "sans-serif")
												.attr("gravity", "center")
												.attr("id", "submit")
												.attr("layout_height", "wrap_content")
												.attr("layout_marginTop", "5dp")
												.attr("layout_width", "match_parent")
												.attr("padding", "10dp")
												.attr("text", string.join_that_server)
												.attr("textAllCaps", "false")
												.attr("textColor", color.colorPrimaryText)
												.attr("textSize", "30sp")
										)
								)
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "createServer")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.create_your_own_server)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "back")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.cancel)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "30sp")
						)
				)
		}

		val switch_server_create: View by lazy {
			ScrollView(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("layout_height", "wrap_content")
				.attr("layout_width", "match_parent")
				.attr("padding", "10dp")
				.attr("layout_id", "switch_server_create")
				.addView(
					LinearLayout(all, null)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "vertical")
						.addView(
							TextView(all, null)
								.attr("gravity", "center")
								.attr("layout_height", "wrap_content")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.server_creation_message)
								.attr("textColor", color.colorPrimary)
								.attr("textSize", "20sp")
						)
						.addView(
							FrameLayout(all, null)
								.attr("background", color.colorPrimary)
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.addView(
									EditText(all, null)
										.attr("hint", string.server_name)
										.attr("id", "name")
										.attr("inputType", "text")
										.attr("layout_height", "wrap_content")
										.attr("layout_width", "match_parent")
										.attr("padding", "15dp")
								)
						)
						.addView(
							FrameLayout(all, null)
								.attr("background", color.colorPrimary)
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "5dp")
								.attr("layout_width", "match_parent")
								.addView(
									EditText(all, null)
										.attr("hint", string.password)
										.attr("id", "password")
										.attr("inputType", "textPassword")
										.attr("layout_height", "wrap_content")
										.attr("layout_width", "match_parent")
										.attr("padding", "15dp")
								)
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorGreen2)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "createServer")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "5dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.create_your_own_server2)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorGreen)
								.attr("textSize", "30sp")
						)
						.addView(
							Button(all, null)
								.attr("background", color.colorPrimary)
								.attr("fontFamily", "sans-serif")
								.attr("gravity", "center")
								.attr("id", "back")
								.attr("layout_height", "wrap_content")
								.attr("layout_marginTop", "10dp")
								.attr("layout_width", "match_parent")
								.attr("padding", "10dp")
								.attr("text", string.cancel)
								.attr("textAllCaps", "false")
								.attr("textColor", color.colorPrimaryText)
								.attr("textSize", "30sp")
						)
				)
		}

		val tree: View by lazy {
			LinearLayout(all, null)
				.attr("background", color.colorPrimaryDark)
				.attr("id", "treeLayout")
				.attr("layout_height", "match_parent")
				.attr("layout_width", "match_parent")
				.attr("orientation", "vertical")
				.attr("layout_id", "tree")
				.addView(
					me.antonio.noack.elementalcommunity.LoadingBarView(all, null)
						.attr("layout_height", "5dp")
						.attr("layout_width", "match_parent")
				)
				.addView(
					LinearLayout(all, null)
						.attr("background", color.colorPrimary)
						.attr("layout_height", "wrap_content")
						.attr("layout_width", "match_parent")
						.attr("orientation", "horizontal")
						.addView(
							ImageView(all, null)
								.attr("contentDescription", string.back)
								.attr("id", "backArrow3")
								.attr("layout_height", "50sp")
								.attr("layout_width", "50sp")
								.attr("src", drawable.ic_arrow_back_black_24dp)
						)
						.addView(
							SeekBar(all, null)
								.attr("id", "spaceSlider")
								.attr("layout_height", "50dp")
								.attr("layout_weight", "1")
								.attr("layout_width", "0dp")
								.attr("progressTint", color.colorPrimaryDark)
								.attr("thumbTint", color.colorPrimaryDark)
						)
						.addView(diamond_bar)
				)
				.addView(
					me.antonio.noack.elementalcommunity.tree.TreeView(all, null)
						.attr("id", "tree")
						.attr("layout_height", "match_parent")
						.attr("layout_width", "match_parent")
				)
		}

		val allLayouts: List<View> by lazy { listOf(
			add_recipe, add_recipe_base, all_pages, ask_override, ask_password, combiner,
			diamond_bar, game, graph, helper_offer, helper_search_recipe, itempedia,
			itempedia_item, itempedia_page, mandala, menu, menu_content, progress_load,
			progress_save, random, recipe_helper, select_file, select_folder, settings,
			show_password, switch_server, switch_server_create, tree,
		) }

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
		const val itempediaTitle = "itempediaTitle"
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
		const val search3 = "search3"
		const val searchButton1 = "searchButton1"
		const val searchButton2 = "searchButton2"
		const val searchButton3 = "searchButton3"
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
					.attr("colorAccent", color.colorAccent)
					.attr("colorPrimary", color.colorPrimary)
					.attr("colorPrimaryDark", color.colorPrimaryDark)
					.attr("id", "AppTheme")
					.attr("parent", "Theme.AppCompat.Light.NoActionBar")
			)
			.addView(
				View(all, null)
					.attr("colorForeground", 0x42221f1f)
					.attr("colorControlActivated", color.colorPrimaryDark)
					.attr("colorSwitchThumbNormal", color.colorPrimaryText)
					.attr("id", "SwitchColors")
					.attr("parent", "Theme.AppCompat.Light.NoActionBar")
			)

	object style {

		const val AppTheme = "AppTheme"
		const val SwitchColors = "SwitchColors"

	}

}
// 0.22981702829468842
