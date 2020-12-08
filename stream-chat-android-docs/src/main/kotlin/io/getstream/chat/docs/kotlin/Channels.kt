package io.getstream.chat.docs.kotlin

import android.util.Log
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.Pagination
import io.getstream.chat.android.client.api.models.QueryChannelRequest
import io.getstream.chat.android.client.api.models.QueryChannelsRequest
import io.getstream.chat.android.client.api.models.QuerySort
import io.getstream.chat.android.client.channel.ChannelClient
import io.getstream.chat.android.client.events.ChannelsMuteEvent
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.ChannelMute
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.Member
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.socket.InitConnectionListener
import io.getstream.chat.android.client.subscribeFor
import io.getstream.chat.android.client.utils.FilterObject
import io.getstream.chat.docs.StaticInstances.TAG

class Channels(val client: ChatClient, val channelController: ChannelClient) {

    /**
     * @see <a href="https://getstream.io/chat/docs/initialize_channel/?language=kotlin">Channel Initialization</a>
     */
    inner class ChannelInitialization {
        fun initialization() {
            // Create channel controller using channel type and channel id
            val channelController = client.channel("channel-type", "channel-id")

            // Or create channel controller using channel cid
            val anotherChannelController = client.channel("cid")
        }
    }

    /**
     * @see <a href="https://getstream.io/chat/docs/creating_channels/?language=kotlin">Creating Channels</a>
     */
    inner class CreatingChannels {
        fun createAChannel() {
            val channelType = "messaging"
            val channelId = "id"
            val extraData = emptyMap<String, Any>()
            client.createChannel(channelType, channelId, extraData).enqueue {
                if (it.isSuccess) {
                    val newChannel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }
    }

    /**
     * @see <a href="https://getstream.io/chat/docs/watch_channel/?language=kotlin">Watching A Channel</a>
     */
    inner class WatchingAChannel {
        fun watchingChannel() {
            channelController.watch().enqueue {
                if (it.isSuccess) {
                    val channel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }

        /**
         * @see <a href="https://getstream.io/chat/docs/watch_channel/?language=kotlin#unwatching">Unwacthing</a>
         */
        fun stopWatchingChannel() {
            channelController.stopWatching().enqueue {
                if (it.isSuccess) {
                    // Channel unwatched
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }
    }

    /**
     * @see <a href="https://getstream.io/chat/docs/query_channels/?language=kotlin">Querying Channels</a>
     */
    inner class QueryingChannels {
        fun queryChannels() {
            val filter = Filters.`in`("members", "thierry").put("type", "messaging")
            val offset = 0
            val limit = 10
            val sort = QuerySort.desc<Channel>("last_message_at")
            val request = QueryChannelsRequest(filter, offset, limit, sort).apply {
                watch = true
                state = true
            }

            client.queryChannels(request).enqueue {
                if (it.isSuccess) {
                    val channels = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }

        /**
         * @see <a href="https://getstream.io/chat/docs/query_channels/?language=kotlin#common-filters-by-use-case">Common Filters</a>
         */
        inner class CommonFilters {
            fun channelsThatContainsSpecificUser() {
                val filter = Filters
                    .`in`("members", "thierry")
                    .put("type", "messaging")
            }

            fun channelsThatWithSpecificStatus() {
                val filter = Filters
                    .`in`("status", "pending", "open", "new")
                    .put("agent_id", "user-id")
            }
        }

        /**
         * @see <a href="https://getstream.io/chat/docs/query_channels/?language=kotlin#response">Response</a>
         */
        fun paginatingChannels() {
            // Get the first 10 channels
            val filter = Filters.`in`("members", "thierry")
            val offset = 0
            val limit = 10
            val request = QueryChannelsRequest(filter, offset, limit)

            client.queryChannels(request).enqueue {
                if (it.isSuccess) {
                    val channels = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }

            // Get the second 10 channels
            val nextOffset = 10
            val nextRequest = QueryChannelsRequest(filter, nextOffset, limit)
            client.queryChannels(nextRequest).enqueue {
                if (it.isSuccess) {
                    val channels = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }
    }

    /**
     * @see <a href="https://getstream.io/chat/docs/channel_pagination/?language=kotlin">Channel Pagination</a>
     */
    inner class ChannelPagination {

        private val pageSize = 10

        // Get the first 10 messages
        fun loadFirstPage() {
            val firstPage = QueryChannelRequest().withMessages(pageSize)
            client.queryChannel("channel-type", "channel-id", firstPage).enqueue {
                if (it.isSuccess) {
                    val messages: List<Message> = it.data().messages
                    if (messages.size < pageSize) {
                        // All messages loaded
                    } else {
                        loadSecondPage(messages.last().id)
                    }
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }

        // Get the second 10 messages
        fun loadSecondPage(lastMessageId: String) {
            val secondPage = QueryChannelRequest().withMessages(Pagination.LESS_THAN, lastMessageId, pageSize)
            client.queryChannel("channel-type", "channel-id", secondPage).enqueue {
                if (it.isSuccess) {
                    val messages: List<Message> = it.data().messages
                    if (messages.size < pageSize) {
                        // All messages loaded
                    } else {
                        // Load another page
                    }
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }
    }

    /**
     * @see <a href="https://getstream.io/chat/docs/channel_update/?language=kotlin">Updating a Channel</a>
     */
    inner class UpdatingAChannel {
        fun updateChannel() {
            val channelData = mapOf("color" to "green")
            val updateMessage = Message().apply {
                text = "Thierry changed the channel color to green"
            }
            channelController.update(updateMessage, channelData).enqueue {
                if (it.isSuccess) {
                    val channel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }
    }

    /**
     * @see <a href="https://getstream.io/chat/docs/channel_members/?language=kotlin">Updating a Channel</a>
     */
    inner class ChangingChannelMembers {

        /**
         * @see <a href="https://getstream.io/chat/docs/channel_members/?language=kotlin#adding-removing-channel-members">Adding & Removing Channel Members</a>
         */
        fun addingAndRemovingChannelMembers() {
            // Add member with id "thierry" and "josh"
            channelController.addMembers("thierry", "josh").enqueue {
                if (it.isSuccess) {
                    val channel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }

            // Remove member with id "thierry" and "josh"
            channelController.removeMembers("thierry", "josh").enqueue {
                if (it.isSuccess) {
                    val channel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }
    }

    /**
     * @see <a href="https://getstream.io/chat/docs/channel_conversations/?language=kotlin">One to One Conversations</a>
     */
    inner class OneToOneConversations {

        /**
         * @see <a href="https://getstream.io/chat/docs/channel_conversations/?language=kotlin#creating-conversations">Creating Conversations</a>
         */
        fun creatingConversation() {
            val members = listOf("thierry", "tomasso")
            val channelType = "messaging"
            client.createChannel(channelType, members).enqueue {
                if (it.isSuccess) {
                    val channel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }
    }

    /**
     * @see <a href="https://getstream.io/chat/docs/channel_conversations/?language=kotlin">Invites</a>
     */
    inner class Invites {

        /**
         * @see <a href="https://getstream.io/chat/docs/channel_invites/?language=kotlin#inviting-users">Iniviting Users</a>
         */
        fun invitingUsers() {
            val members = listOf("thierry", "tommaso")
            val invites = listOf("nick")
            val data = mapOf(
                "members" to members,
                "invites" to invites
            )

            channelController.create(data).enqueue {
                if (it.isSuccess) {
                    val channel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }

        /**
         * @see <a href="https://getstream.io/chat/docs/channel_invites/?language=kotlin#accepting-an-invite">Accept an Invite</a>
         */
        fun acceptingAnInvite() {
            channelController.acceptInvite("Nick joined this channel!").enqueue {
                if (it.isSuccess) {
                    val channel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }

        /**
         * @see <a href="https://getstream.io/chat/docs/channel_invites/?language=kotlin#rejecting-an-invite">Rejecting an Invite</a>
         */
        fun rejectingAnInvite() {
            channelController.rejectInvite().enqueue {
                if (it.isSuccess) {
                    val channel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }

        /**
         * @see <a href="https://getstream.io/chat/docs/channel_invites/?language=kotlin#query-for-accepted-invites">Query For Accepted Invites</a>
         */
        fun queryForAcceptedInvites() {
            val offset = 0
            val limit = 10
            val request = QueryChannelsRequest(FilterObject("invite", "accepted"), offset, limit)
            client.queryChannels(request).enqueue {
                if (it.isSuccess) {
                    val channels = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }

        /**
         * @see <a href="https://getstream.io/chat/docs/channel_invites/?language=kotlin#query-for-rejected-invites">Query For Rejected Invites</a>
         */
        fun queryForRejectedInvites() {
            val offset = 0
            val limit = 10
            val request = QueryChannelsRequest(FilterObject("invite", "rejected"), offset, limit)
            client.queryChannels(request).enqueue {
                if (it.isSuccess) {
                    val channels = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }
    }

    /**
     * @see <a href="https://getstream.io/chat/docs/channel_delete/?language=kotlin">Deleting & Hiding a Channel</a>
     */
    inner class DeletingAndHidingAChannel {

        fun deletingAChannel() {
            channelController.delete().enqueue {
                if (it.isSuccess) {
                    val channel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }

        /**
         * @see <a href="https://getstream.io/chat/docs/channel_delete/?language=kotlin#hiding-a-channel">Hiding a Channel</a>
         */
        fun hidingAChannel() {
            // Hides the channel until a new message is added there
            channelController.hide().enqueue {
                if (it.isSuccess) {
                    val channel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }

            // Shows a previously hidden channel
            channelController.show().enqueue {
                if (it.isSuccess) {
                    val channel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }

            // Hide the channel and clear the message history
            channelController.hide(clearHistory = true).enqueue {
                if (it.isSuccess) {
                    val channel = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }
    }

    /**
     * @see <a href="https://getstream.io/chat/docs/muting_channels/?language=kotlin">Muting Channels</a>
     */
    inner class MutingChannels {

        /**
         * @see <a href="https://getstream.io/chat/docs/muting_channels/?language=kotlin#channel-mute">Channel Mute</a>
         */
        fun channelMute() {
            client.muteChannel("channel-type", "channel-id").enqueue {
                if (it.isSuccess) {
                    // Channel is muted
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }

            // Get list of muted channels when user is connected
            client.setUser(
                User("user-id"),
                "token",
                object : InitConnectionListener() {
                    override fun onSuccess(data: ConnectionData) {
                        val user = data.user
                        // Mutes contains the list of channel mutes
                        val mutes: List<ChannelMute> = user.channelMutes
                    }
                }
            )

            // Get updates about muted channels
            client.subscribeFor<ChannelsMuteEvent> {
                val mutes: List<ChannelMute> = it.channelsMute
            }
        }

        /**
         * @see <a href="https://getstream.io/chat/docs/muting_channels/?language=kotlin#query-muted-channels">Query Muted Channels</a>
         */
        fun queryMutedChannels() {
            // Retrieve channels excluding muted ones
            val offset = 0
            val limit = 10
            val notMutedFilter = Filters.eq("muted", false)
            client.queryChannels(QueryChannelsRequest(notMutedFilter, offset, limit)).enqueue {
                if (it.isSuccess) {
                    val channels = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }

            // Retrieve muted channels
            val mutedFilter = Filters.eq("muted", true)
            client.queryChannels(QueryChannelsRequest(mutedFilter, offset, limit)).enqueue {
                if (it.isSuccess) {
                    val channels = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }

        /**
         * @see <a href="https://getstream.io/chat/docs/muting_channels/?language=kotlin#remove-a-channel-mute">Remove a Channel Mute</a>
         */
        fun removeAChannelMute() {
            // Unmute channel for current user
            channelController.unmute().enqueue {
                if (it.isSuccess) {
                    // Channel is unmuted
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }
        }
    }

    /**
     * @see <a href="https://getstream.io/chat/docs/query_members/?language=kotlin">Query Members</a>
     */
    inner class QueryMembers {
        fun queryingMembers() {
            val offset = 0
            val limit = 10
            val sort = QuerySort<Member>()

            // Query members by user.name
            channelController.queryMembers(offset, limit, Filters.eq("name", "tommaso"), sort, emptyList()).enqueue {
                if (it.isSuccess) {
                    val members = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }

            // Autocomplete members by user name
            channelController.queryMembers(offset, limit, Filters.autocomplete("name", "tommaso"), sort, emptyList())
                .enqueue {
                    if (it.isSuccess) {
                        val members = it.data()
                    } else {
                        Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                    }
                }

            // Query member by id
            channelController.queryMembers(offset, limit, Filters.eq("id", "tommaso"), sort, emptyList()).enqueue {
                if (it.isSuccess) {
                    val members = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }

            // Query multiple members by id
            channelController.queryMembers(offset, limit, Filters.`in`("id", "tommaso", "thierry"), sort, emptyList())
                .enqueue {
                    if (it.isSuccess) {
                        val members = it.data()
                    } else {
                        Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                    }
                }

            // Query channel moderators
            channelController.queryMembers(offset, limit, Filters.eq("is_moderator", true), sort, emptyList()).enqueue {
                if (it.isSuccess) {
                    val members = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }

            // Query for banned members in channel
            channelController.queryMembers(offset, limit, Filters.eq("banned", true), sort, emptyList()).enqueue {
                if (it.isSuccess) {
                    val members = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }

            // Query members with pending invites
            channelController.queryMembers(offset, limit, Filters.eq("invite", "pending"), sort, emptyList()).enqueue {
                if (it.isSuccess) {
                    val members = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }

            // Query all the members
            channelController.queryMembers(offset, limit, FilterObject(), sort, emptyList()).enqueue {
                if (it.isSuccess) {
                    val members = it.data()
                } else {
                    Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                }
            }

            // Order results by member created at descending
            val createdAtDescendingSort = QuerySort.desc<Member>("created_at")
            channelController.queryMembers(offset, limit, FilterObject(), createdAtDescendingSort, emptyList())
                .enqueue {
                    if (it.isSuccess) {
                        val members = it.data()
                    } else {
                        Log.e(TAG, String.format("There was an error %s", it.error(), it.error().cause))
                    }
                }
        }
    }
}
