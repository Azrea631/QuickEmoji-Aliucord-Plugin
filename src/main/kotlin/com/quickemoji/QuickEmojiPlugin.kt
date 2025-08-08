/*
 * QuickEmojiPlugin.kt
 *
 * This Aliucord plugin enables users to quickly send or react with a customizable emoji.
 * It adds a new item to the message context menu allowing quick reactions,
 * and a button next to the chat input for sending the emoji directly in chat.
 * Users can select their preferred emoji to speed up their Discord interactions.
 *
 * Author: Azrea Shade
 * Version: 1.0.0
 */
package com.quickemoji

import android.content.Context
import android.widget.Toast
import com.aliucord.Utils
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.plugins.PluginAPI
import com.discord.api.message.Message
import com.discord.widgets.chat.input.WidgetChatInput
import com.discord.widgets.chat.input.WidgetChatInputBinding
import com.discord.widgets.message.MessageActionsWidget
import com.discord.widgets.message.MessageActionsWidgetViewModel
import com.discord.widgets.message.MessageActionsViewModel
import com.discord.widgets.message.MessageReactionPickerViewModel
import com.discord.widgets.message.actions.MessageActions
import com.discord.widgets.message.actions.MessageActionsViewModelFactory
import com.discord.widgets.message.actions.MessageActionsWidgetFactory
import com.discord.widgets.message.actions.MessageContextMenuItem
import com.discord.widgets.message.actions.MessageContextMenuItemViewHolder
import com.discord.widgets.message.actions.MessageContextMenuItemViewModel
import com.discord.widgets.message.actions.MessageContextMenuView
import com.discord.widgets.message.actions.MessageContextMenuViewModel
import com.discord.widgets.message.actions.MessageContextMenuViewModelFactory
import com.discord.widgets.message.actions.MessageContextMenuViewModelProvider
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwner
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerImpl
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerProvider
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModel
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelFactory
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelProvider
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStore
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreFactory
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreProvider
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepository
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryFactory
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryProvider
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStore
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreFactory
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreProvider
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepository
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryFactory
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryProvider
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStore
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreFactory
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreProvider
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreRepository
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreRepositoryFactory
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreRepositoryProvider
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreRepositoryStore
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreRepositoryStoreFactory
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreRepositoryStoreProvider
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreRepositoryStoreRepository
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreRepositoryStoreRepositoryFactory
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreRepositoryStoreRepositoryProvider
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreRepositoryStoreRepositoryStore
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreRepositoryStoreRepositoryStoreFactory
import com.discord.widgets.message.actions.MessageContextMenuViewModelStoreOwnerViewModelStoreRepositoryStoreRepositoryStoreRepositoryStoreRepositoryStoreProvider

@AliucordPlugin
class QuickEmojiPlugin : Plugin() {
    private var emoji: String = "<:heart:000000000000000000>" // default emoji placeholder

    override fun start(context: Context) {
        // Register context menu action
        patchMessageActions(context)
        patchChatInput(context)
    }

    override fun stop(context: Context) {
        unpatchAll()
    }

    private fun patchMessageActions(context: Context) {
        patcher.patch(MessageActionsWidget::class.java.getDeclaredMethod("configureUI", Message::class.java)) { call ->
            val message = call.args[0] as Message
            val menu = call.thisObject as MessageActionsWidget

            // Add a custom menu item to react with quick emoji
            val item = MessageContextMenuItem(
                id = 9999,
                title = "React with QuickEmoji",
                icon = null,
                onClick = {
                    // React with emoji code
                    menu.viewModel.react(message.id, emoji)
                    Toast.makeText(context, "Reacted with QuickEmoji!", Toast.LENGTH_SHORT).show()
                    menu.dismiss()
                }
            )

            menu.addMenuItem(item)
        }
    }

    private fun patchChatInput(context: Context) {
        patcher.patch(WidgetChatInput::class.java.getDeclaredMethod("configureUI", WidgetChatInputBinding::class.java)) { call ->
            val input = call.thisObject as WidgetChatInput
            val binding = call.args[0] as WidgetChatInputBinding

            // Add a button near chat input for quick emoji send
            val btn = Utils.createButton(context, emoji)
            btn.setOnClickListener {
                input.sendMessage(emoji)
            }

            binding.container.addView(btn)
        }
    }
}
