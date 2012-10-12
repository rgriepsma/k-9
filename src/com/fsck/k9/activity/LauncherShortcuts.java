package com.fsck.k9.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.fsck.k9.Account;
import com.fsck.k9.BaseAccount;
import com.fsck.k9.R;
import com.fsck.k9.search.SearchSpecification;

public class LauncherShortcuts extends AccountList {
    @Override
    public void onCreate(Bundle icicle) {
        // finish() immediately if we aren't supposed to be here
        if (!Intent.ACTION_CREATE_SHORTCUT.equals(getIntent().getAction())) {
            finish();
            return;
        }

        super.onCreate(icicle);
    }

    @Override
    protected boolean displaySpecialAccounts() {
        return true;
    }

    @Override
    protected void onAccountSelected(BaseAccount account) {
        Intent shortcutIntent = null;

        if (account instanceof SearchSpecification) {
            shortcutIntent = MessageList.actionHandleAccountIntent(this, account.getDescription(),
                    (SearchSpecification) account);
        } else {
            shortcutIntent = FolderList.actionHandleAccountIntent(this, (Account) account, null,
                    true);
        }

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        String description = account.getDescription();
        if (description == null || description.length() == 0) {
            description = account.getEmail();
        }
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, description);
        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);

        setResult(RESULT_OK, intent);
        finish();
    }
}
