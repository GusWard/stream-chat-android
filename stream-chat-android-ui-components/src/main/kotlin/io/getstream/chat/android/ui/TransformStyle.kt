/*
 * Copyright (c) 2014-2022 Stream.io Inc. All rights reserved.
 *
 * Licensed under the Stream License;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://github.com/GetStream/stream-chat-android/blob/main/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.chat.android.ui

import io.getstream.chat.android.ui.avatar.AvatarStyle
import io.getstream.chat.android.ui.channel.list.ChannelActionsDialogViewStyle
import io.getstream.chat.android.ui.channel.list.ChannelListViewStyle
import io.getstream.chat.android.ui.gallery.AttachmentGalleryViewMediaStyle
import io.getstream.chat.android.ui.gallery.MediaAttachmentGridViewStyle
import io.getstream.chat.android.ui.gallery.options.AttachmentGalleryOptionsViewStyle
import io.getstream.chat.android.ui.mention.list.MentionListViewStyle
import io.getstream.chat.android.ui.message.composer.MessageComposerViewStyle
import io.getstream.chat.android.ui.message.composer.attachment.picker.AttachmentsPickerDialogStyle
import io.getstream.chat.android.ui.message.list.DefaultQuotedAttachmentViewStyle
import io.getstream.chat.android.ui.message.list.FileAttachmentViewStyle
import io.getstream.chat.android.ui.message.list.GiphyViewHolderStyle
import io.getstream.chat.android.ui.message.list.MessageListItemStyle
import io.getstream.chat.android.ui.message.list.MessageListViewStyle
import io.getstream.chat.android.ui.message.list.MessageReplyStyle
import io.getstream.chat.android.ui.message.list.ScrollButtonViewStyle
import io.getstream.chat.android.ui.message.list.UnsupportedAttachmentViewStyle
import io.getstream.chat.android.ui.message.list.adapter.view.MediaAttachmentViewStyle
import io.getstream.chat.android.ui.message.list.header.MessageListHeaderViewStyle
import io.getstream.chat.android.ui.message.list.reactions.edit.EditReactionsViewStyle
import io.getstream.chat.android.ui.message.list.reactions.user.SingleReactionViewStyle
import io.getstream.chat.android.ui.message.list.reactions.view.ViewReactionsViewStyle
import io.getstream.chat.android.ui.pinned.list.PinnedMessageListViewStyle
import io.getstream.chat.android.ui.search.SearchInputViewStyle
import io.getstream.chat.android.ui.search.list.SearchResultListViewStyle
import io.getstream.chat.android.ui.typing.TypingIndicatorViewStyle

public object TransformStyle {
    @JvmStatic
    public var avatarStyleTransformer: StyleTransformer<AvatarStyle> = noopTransformer()

    @JvmStatic
    public var channelListStyleTransformer: StyleTransformer<ChannelListViewStyle> = noopTransformer()

    @JvmStatic
    public var messageListStyleTransformer: StyleTransformer<MessageListViewStyle> = noopTransformer()

    @JvmStatic
    public var messageListItemStyleTransformer: StyleTransformer<MessageListItemStyle> = noopTransformer()

    @JvmStatic
    public var scrollButtonStyleTransformer: StyleTransformer<ScrollButtonViewStyle> = noopTransformer()

    @JvmStatic
    public var viewReactionsStyleTransformer: StyleTransformer<ViewReactionsViewStyle> = noopTransformer()

    @JvmStatic
    public var editReactionsStyleTransformer: StyleTransformer<EditReactionsViewStyle> = noopTransformer()

    @JvmStatic
    public var singleReactionViewStyleTransformer: StyleTransformer<SingleReactionViewStyle> = noopTransformer()

    @JvmStatic
    public var channelActionsDialogStyleTransformer: StyleTransformer<ChannelActionsDialogViewStyle> = noopTransformer()

    @JvmStatic
    public var giphyViewHolderStyleTransformer: StyleTransformer<GiphyViewHolderStyle> = noopTransformer()

    @JvmStatic
    public var mediaAttachmentStyleTransformer: StyleTransformer<MediaAttachmentViewStyle> = noopTransformer()

    @JvmStatic
    public var messageReplyStyleTransformer: StyleTransformer<MessageReplyStyle> = noopTransformer()

    @JvmStatic
    public var fileAttachmentStyleTransformer: StyleTransformer<FileAttachmentViewStyle> = noopTransformer()

    @JvmStatic
    public var unsupportedAttachmentStyleTransformer: StyleTransformer<UnsupportedAttachmentViewStyle> =
        noopTransformer()

    @JvmStatic
    public var messageListHeaderStyleTransformer: StyleTransformer<MessageListHeaderViewStyle> = noopTransformer()

    @JvmStatic
    public var mentionListViewStyleTransformer: StyleTransformer<MentionListViewStyle> = noopTransformer()

    @JvmStatic
    public var searchInputViewStyleTransformer: StyleTransformer<SearchInputViewStyle> = noopTransformer()

    @JvmStatic
    public var searchResultListViewStyleTransformer: StyleTransformer<SearchResultListViewStyle> = noopTransformer()

    @JvmStatic
    public var typingIndicatorViewStyleTransformer: StyleTransformer<TypingIndicatorViewStyle> = noopTransformer()

    @JvmStatic
    public var pinnedMessageListViewStyleTransformer: StyleTransformer<PinnedMessageListViewStyle> = noopTransformer()

    @JvmStatic
    public var defaultQuotedAttachmentViewStyleTransformer: StyleTransformer<DefaultQuotedAttachmentViewStyle> =
        noopTransformer()

    @JvmStatic
    public var attachmentGalleryOptionsStyleTransformer: StyleTransformer<AttachmentGalleryOptionsViewStyle> =
        noopTransformer()

    @JvmStatic
    public var messageComposerStyleTransformer: StyleTransformer<MessageComposerViewStyle> = noopTransformer()

    @JvmStatic
    public var attachmentsPickerStyleTransformer: StyleTransformer<AttachmentsPickerDialogStyle> = noopTransformer()

    @JvmStatic
    public var attachmentGalleryViewMediaStyle: StyleTransformer<AttachmentGalleryViewMediaStyle> = noopTransformer()

    @JvmStatic
    public var mediaAttachmentGridViewStyle: StyleTransformer<MediaAttachmentGridViewStyle> = noopTransformer()

    private fun <T> noopTransformer() = StyleTransformer<T> { it }
}

public fun interface StyleTransformer<T> {
    public fun transform(source: T): T
}
