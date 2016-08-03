package com.sdklite.firkit;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.app.Activity;
import android.app.PackageInstallObserver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ContainerEncryptionParams;
import android.content.pm.FeatureInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.InstrumentationInfo;
import android.content.pm.KeySet;
import android.content.pm.ManifestDigest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.VerificationParams;
import android.content.pm.VerifierDeviceIdentity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.UserHandle;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/test/AndroidManifest.xml", sdk = 21)
public class FirKitTest {

    private class PackageManagerWrapper extends PackageManager {
        
        private final PackageManager delegate;

        private final String packageName;

        public PackageManagerWrapper(final PackageManager pm, final String packageName) {
            this.delegate = pm;
            this.packageName = packageName;
        }
        public int hashCode() {
            return delegate.hashCode();
        }

        public boolean equals(Object obj) {
            return delegate.equals(obj);
        }

        public String toString() {
            return delegate.toString();
        }

        public PackageInfo getPackageInfo(String packageName, int flags) throws NameNotFoundException {
            return delegate.getPackageInfo(this.packageName, flags);
        }

        public String[] currentToCanonicalPackageNames(String[] names) {
            return delegate.currentToCanonicalPackageNames(names);
        }

        public String[] canonicalToCurrentPackageNames(String[] names) {
            return delegate.canonicalToCurrentPackageNames(names);
        }

        public Intent getLaunchIntentForPackage(String packageName) {
            return delegate.getLaunchIntentForPackage(this.packageName);
        }

        public Intent getLeanbackLaunchIntentForPackage(String packageName) {
            return delegate.getLeanbackLaunchIntentForPackage(this.packageName);
        }

        public int[] getPackageGids(String packageName) throws NameNotFoundException {
            return delegate.getPackageGids(this.packageName);
        }

        public int getPackageUid(String packageName, int userHandle) throws NameNotFoundException {
            return delegate.getPackageUid(this.packageName, userHandle);
        }

        public PermissionInfo getPermissionInfo(String name, int flags) throws NameNotFoundException {
            return delegate.getPermissionInfo(name, flags);
        }

        public List<PermissionInfo> queryPermissionsByGroup(String group, int flags) throws NameNotFoundException {
            return delegate.queryPermissionsByGroup(group, flags);
        }

        public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) throws NameNotFoundException {
            return delegate.getPermissionGroupInfo(name, flags);
        }

        public List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
            return delegate.getAllPermissionGroups(flags);
        }

        public ApplicationInfo getApplicationInfo(String packageName, int flags) throws NameNotFoundException {
            return delegate.getApplicationInfo(this.packageName, flags);
        }

        public ActivityInfo getActivityInfo(ComponentName component, int flags) throws NameNotFoundException {
            return delegate.getActivityInfo(component, flags);
        }

        public ActivityInfo getReceiverInfo(ComponentName component, int flags) throws NameNotFoundException {
            return delegate.getReceiverInfo(component, flags);
        }

        public ServiceInfo getServiceInfo(ComponentName component, int flags) throws NameNotFoundException {
            return delegate.getServiceInfo(component, flags);
        }

        public ProviderInfo getProviderInfo(ComponentName component, int flags) throws NameNotFoundException {
            return delegate.getProviderInfo(component, flags);
        }

        public List<PackageInfo> getInstalledPackages(int flags) {
            return delegate.getInstalledPackages(flags);
        }

        public List<PackageInfo> getPackagesHoldingPermissions(String[] permissions, int flags) {
            return delegate.getPackagesHoldingPermissions(permissions, flags);
        }

        public List<PackageInfo> getInstalledPackages(int flags, int userId) {
            return delegate.getInstalledPackages(flags, userId);
        }

        public int checkPermission(String permName, String pkgName) {
            return delegate.checkPermission(permName, pkgName);
        }

        public boolean addPermission(PermissionInfo info) {
            return delegate.addPermission(info);
        }

        public boolean addPermissionAsync(PermissionInfo info) {
            return delegate.addPermissionAsync(info);
        }

        public void removePermission(String name) {
            delegate.removePermission(name);
        }

        public Intent buildPermissionRequestIntent(String... permissions) {
            return delegate.buildPermissionRequestIntent(permissions);
        }

        public void grantPermission(String packageName, String permissionName) {
            delegate.grantPermission(this.packageName, permissionName);
        }

        public void revokePermission(String packageName, String permissionName) {
            delegate.revokePermission(this.packageName, permissionName);
        }

        public int checkSignatures(String pkg1, String pkg2) {
            return delegate.checkSignatures(pkg1, pkg2);
        }

        public int checkSignatures(int uid1, int uid2) {
            return delegate.checkSignatures(uid1, uid2);
        }

        public String[] getPackagesForUid(int uid) {
            return delegate.getPackagesForUid(uid);
        }

        public String getNameForUid(int uid) {
            return delegate.getNameForUid(uid);
        }

        public int getUidForSharedUser(String sharedUserName) throws NameNotFoundException {
            return delegate.getUidForSharedUser(sharedUserName);
        }

        public List<ApplicationInfo> getInstalledApplications(int flags) {
            return delegate.getInstalledApplications(flags);
        }

        public String[] getSystemSharedLibraryNames() {
            return delegate.getSystemSharedLibraryNames();
        }

        public FeatureInfo[] getSystemAvailableFeatures() {
            return delegate.getSystemAvailableFeatures();
        }

        public boolean hasSystemFeature(String name) {
            return delegate.hasSystemFeature(name);
        }

        public ResolveInfo resolveActivity(Intent intent, int flags) {
            return delegate.resolveActivity(intent, flags);
        }

        public ResolveInfo resolveActivityAsUser(Intent intent, int flags, int userId) {
            return delegate.resolveActivityAsUser(intent, flags, userId);
        }

        public List<ResolveInfo> queryIntentActivities(Intent intent, int flags) {
            return delegate.queryIntentActivities(intent, flags);
        }

        public List<ResolveInfo> queryIntentActivitiesAsUser(Intent intent, int flags, int userId) {
            return delegate.queryIntentActivitiesAsUser(intent, flags, userId);
        }

        public List<ResolveInfo> queryIntentActivityOptions(ComponentName caller, Intent[] specifics, Intent intent,
                int flags) {
            return delegate.queryIntentActivityOptions(caller, specifics, intent, flags);
        }

        public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int flags) {
            return delegate.queryBroadcastReceivers(intent, flags);
        }

        public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int flags, int userId) {
            return delegate.queryBroadcastReceivers(intent, flags, userId);
        }

        public ResolveInfo resolveService(Intent intent, int flags) {
            return delegate.resolveService(intent, flags);
        }

        public List<ResolveInfo> queryIntentServices(Intent intent, int flags) {
            return delegate.queryIntentServices(intent, flags);
        }

        public List<ResolveInfo> queryIntentServicesAsUser(Intent intent, int flags, int userId) {
            return delegate.queryIntentServicesAsUser(intent, flags, userId);
        }

        public List<ResolveInfo> queryIntentContentProvidersAsUser(Intent intent, int flags, int userId) {
            return delegate.queryIntentContentProvidersAsUser(intent, flags, userId);
        }

        public List<ResolveInfo> queryIntentContentProviders(Intent intent, int flags) {
            return delegate.queryIntentContentProviders(intent, flags);
        }

        public ProviderInfo resolveContentProvider(String name, int flags) {
            return delegate.resolveContentProvider(name, flags);
        }

        public ProviderInfo resolveContentProviderAsUser(String name, int flags, int userId) {
            return delegate.resolveContentProviderAsUser(name, flags, userId);
        }

        public List<ProviderInfo> queryContentProviders(String processName, int uid, int flags) {
            return delegate.queryContentProviders(processName, uid, flags);
        }

        public InstrumentationInfo getInstrumentationInfo(ComponentName className, int flags)
                throws NameNotFoundException {
            return delegate.getInstrumentationInfo(className, flags);
        }

        public List<InstrumentationInfo> queryInstrumentation(String targetPackage, int flags) {
            return delegate.queryInstrumentation(targetPackage, flags);
        }

        public Drawable getDrawable(String packageName, int resid, ApplicationInfo appInfo) {
            return delegate.getDrawable(this.packageName, resid, appInfo);
        }

        public Drawable getActivityIcon(ComponentName activityName) throws NameNotFoundException {
            return delegate.getActivityIcon(activityName);
        }

        public Drawable getActivityIcon(Intent intent) throws NameNotFoundException {
            return delegate.getActivityIcon(intent);
        }

        public Drawable getActivityBanner(ComponentName activityName) throws NameNotFoundException {
            return delegate.getActivityBanner(activityName);
        }

        public Drawable getActivityBanner(Intent intent) throws NameNotFoundException {
            return delegate.getActivityBanner(intent);
        }

        public Drawable getDefaultActivityIcon() {
            return delegate.getDefaultActivityIcon();
        }

        public Drawable getApplicationIcon(ApplicationInfo info) {
            return delegate.getApplicationIcon(info);
        }

        public Drawable getApplicationIcon(String packageName) throws NameNotFoundException {
            return delegate.getApplicationIcon(this.packageName);
        }

        public Drawable getApplicationBanner(ApplicationInfo info) {
            return delegate.getApplicationBanner(info);
        }

        public Drawable getApplicationBanner(String packageName) throws NameNotFoundException {
            return delegate.getApplicationBanner(this.packageName);
        }

        public Drawable getActivityLogo(ComponentName activityName) throws NameNotFoundException {
            return delegate.getActivityLogo(activityName);
        }

        public Drawable getActivityLogo(Intent intent) throws NameNotFoundException {
            return delegate.getActivityLogo(intent);
        }

        public Drawable getApplicationLogo(ApplicationInfo info) {
            return delegate.getApplicationLogo(info);
        }

        public Drawable getApplicationLogo(String packageName) throws NameNotFoundException {
            return delegate.getApplicationLogo(this.packageName);
        }

        public Drawable getUserBadgedIcon(Drawable icon, UserHandle user) {
            return delegate.getUserBadgedIcon(icon, user);
        }

        public Drawable getUserBadgedDrawableForDensity(Drawable drawable, UserHandle user, Rect badgeLocation,
                int badgeDensity) {
            return delegate.getUserBadgedDrawableForDensity(drawable, user, badgeLocation, badgeDensity);
        }

        public Drawable getUserBadgeForDensity(UserHandle user, int density) {
            return delegate.getUserBadgeForDensity(user, density);
        }

        public CharSequence getUserBadgedLabel(CharSequence label, UserHandle user) {
            return delegate.getUserBadgedLabel(label, user);
        }

        public CharSequence getText(String packageName, int resid, ApplicationInfo appInfo) {
            return delegate.getText(this.packageName, resid, appInfo);
        }

        public XmlResourceParser getXml(String packageName, int resid, ApplicationInfo appInfo) {
            return delegate.getXml(this.packageName, resid, appInfo);
        }

        public CharSequence getApplicationLabel(ApplicationInfo info) {
            return delegate.getApplicationLabel(info);
        }

        public Resources getResourcesForActivity(ComponentName activityName) throws NameNotFoundException {
            return delegate.getResourcesForActivity(activityName);
        }

        public Resources getResourcesForApplication(ApplicationInfo app) throws NameNotFoundException {
            return delegate.getResourcesForApplication(app);
        }

        public Resources getResourcesForApplication(String appPackageName) throws NameNotFoundException {
            return delegate.getResourcesForApplication(this.packageName);
        }

        public Resources getResourcesForApplicationAsUser(String appPackageName, int userId)
                throws NameNotFoundException {
            return delegate.getResourcesForApplicationAsUser(this.packageName, userId);
        }

        public PackageInfo getPackageArchiveInfo(String archiveFilePath, int flags) {
            return delegate.getPackageArchiveInfo(archiveFilePath, flags);
        }

        public void installPackage(Uri packageURI, IPackageInstallObserver observer, int flags,
                String installerPackageName) {
            delegate.installPackage(packageURI, observer, flags, installerPackageName);
        }

        public void installPackageWithVerification(Uri packageURI, IPackageInstallObserver observer, int flags,
                String installerPackageName, Uri verificationURI, ManifestDigest manifestDigest,
                ContainerEncryptionParams encryptionParams) {
            delegate.installPackageWithVerification(packageURI, observer, flags, installerPackageName, verificationURI,
                    manifestDigest, encryptionParams);
        }

        public void installPackageWithVerificationAndEncryption(Uri packageURI, IPackageInstallObserver observer,
                int flags, String installerPackageName, VerificationParams verificationParams,
                ContainerEncryptionParams encryptionParams) {
            delegate.installPackageWithVerificationAndEncryption(packageURI, observer, flags, installerPackageName,
                    verificationParams, encryptionParams);
        }

        public void installPackage(Uri packageURI, PackageInstallObserver observer, int flags,
                String installerPackageName) {
            delegate.installPackage(packageURI, observer, flags, installerPackageName);
        }

        public void installPackageWithVerification(Uri packageURI, PackageInstallObserver observer, int flags,
                String installerPackageName, Uri verificationURI, ManifestDigest manifestDigest,
                ContainerEncryptionParams encryptionParams) {
            delegate.installPackageWithVerification(packageURI, observer, flags, installerPackageName, verificationURI,
                    manifestDigest, encryptionParams);
        }

        public void installPackageWithVerificationAndEncryption(Uri packageURI, PackageInstallObserver observer,
                int flags, String installerPackageName, VerificationParams verificationParams,
                ContainerEncryptionParams encryptionParams) {
            delegate.installPackageWithVerificationAndEncryption(packageURI, observer, flags, installerPackageName,
                    verificationParams, encryptionParams);
        }

        public int installExistingPackage(String packageName) throws NameNotFoundException {
            return delegate.installExistingPackage(this.packageName);
        }

        public void verifyPendingInstall(int id, int verificationCode) {
            delegate.verifyPendingInstall(id, verificationCode);
        }

        public void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay) {
            delegate.extendVerificationTimeout(id, verificationCodeAtTimeout, millisecondsToDelay);
        }

        public void setInstallerPackageName(String targetPackage, String installerPackageName) {
            delegate.setInstallerPackageName(targetPackage, installerPackageName);
        }

        public void deletePackage(String packageName, IPackageDeleteObserver observer, int flags) {
            delegate.deletePackage(this.packageName, observer, flags);
        }

        public String getInstallerPackageName(String packageName) {
            return delegate.getInstallerPackageName(this.packageName);
        }

        public void clearApplicationUserData(String packageName, IPackageDataObserver observer) {
            delegate.clearApplicationUserData(this.packageName, observer);
        }

        public void deleteApplicationCacheFiles(String packageName, IPackageDataObserver observer) {
            delegate.deleteApplicationCacheFiles(this.packageName, observer);
        }

        public void freeStorageAndNotify(long freeStorageSize, IPackageDataObserver observer) {
            delegate.freeStorageAndNotify(freeStorageSize, observer);
        }

        public void freeStorage(long freeStorageSize, IntentSender pi) {
            delegate.freeStorage(freeStorageSize, pi);
        }

        public void getPackageSizeInfo(String packageName, int userHandle, IPackageStatsObserver observer) {
            delegate.getPackageSizeInfo(this.packageName, userHandle, observer);
        }

        public void getPackageSizeInfo(String packageName, IPackageStatsObserver observer) {
            delegate.getPackageSizeInfo(this.packageName, observer);
        }

        public void addPackageToPreferred(String packageName) {
            delegate.addPackageToPreferred(this.packageName);
        }

        public void removePackageFromPreferred(String packageName) {
            delegate.removePackageFromPreferred(this.packageName);
        }

        public List<PackageInfo> getPreferredPackages(int flags) {
            return delegate.getPreferredPackages(flags);
        }

        public void addPreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity) {
            delegate.addPreferredActivity(filter, match, set, activity);
        }

        public void addPreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity,
                int userId) {
            delegate.addPreferredActivity(filter, match, set, activity, userId);
        }

        public void replacePreferredActivity(IntentFilter filter, int match, ComponentName[] set,
                ComponentName activity) {
            delegate.replacePreferredActivity(filter, match, set, activity);
        }

        public void replacePreferredActivityAsUser(IntentFilter filter, int match, ComponentName[] set,
                ComponentName activity, int userId) {
            delegate.replacePreferredActivityAsUser(filter, match, set, activity, userId);
        }

        public void clearPackagePreferredActivities(String packageName) {
            delegate.clearPackagePreferredActivities(this.packageName);
        }

        public int getPreferredActivities(List<IntentFilter> outFilters, List<ComponentName> outActivities,
                String packageName) {
            return delegate.getPreferredActivities(outFilters, outActivities, this.packageName);
        }

        public ComponentName getHomeActivities(List<ResolveInfo> outActivities) {
            return delegate.getHomeActivities(outActivities);
        }

        public void setComponentEnabledSetting(ComponentName componentName, int newState, int flags) {
            delegate.setComponentEnabledSetting(componentName, newState, flags);
        }

        public int getComponentEnabledSetting(ComponentName componentName) {
            return delegate.getComponentEnabledSetting(componentName);
        }

        public void setApplicationEnabledSetting(String packageName, int newState, int flags) {
            delegate.setApplicationEnabledSetting(this.packageName, newState, flags);
        }

        public int getApplicationEnabledSetting(String packageName) {
            return delegate.getApplicationEnabledSetting(this.packageName);
        }

        public boolean setApplicationHiddenSettingAsUser(String packageName, boolean hidden, UserHandle userHandle) {
            return delegate.setApplicationHiddenSettingAsUser(this.packageName, hidden, userHandle);
        }

        public boolean getApplicationHiddenSettingAsUser(String packageName, UserHandle userHandle) {
            return delegate.getApplicationHiddenSettingAsUser(this.packageName, userHandle);
        }

        public boolean isSafeMode() {
            return delegate.isSafeMode();
        }

        public KeySet getKeySetByAlias(String packageName, String alias) {
            return delegate.getKeySetByAlias(this.packageName, alias);
        }

        public KeySet getSigningKeySet(String packageName) {
            return delegate.getSigningKeySet(this.packageName);
        }

        public boolean isSignedBy(String packageName, KeySet ks) {
            return delegate.isSignedBy(this.packageName, ks);
        }

        public boolean isSignedByExactly(String packageName, KeySet ks) {
            return delegate.isSignedByExactly(this.packageName, ks);
        }

        public void movePackage(String packageName, IPackageMoveObserver observer, int flags) {
            delegate.movePackage(this.packageName, observer, flags);
        }

        public VerifierDeviceIdentity getVerifierDeviceIdentity() {
            return delegate.getVerifierDeviceIdentity();
        }

        public PackageInstaller getPackageInstaller() {
            return delegate.getPackageInstaller();
        }

        public void addCrossProfileIntentFilter(IntentFilter filter, int sourceUserId, int targetUserId, int flags) {
            delegate.addCrossProfileIntentFilter(filter, sourceUserId, targetUserId, flags);
        }

        public void clearCrossProfileIntentFilters(int sourceUserId) {
            delegate.clearCrossProfileIntentFilters(sourceUserId);
        }

        public Drawable loadItemIcon(PackageItemInfo itemInfo, ApplicationInfo appInfo) {
            return delegate.loadItemIcon(itemInfo, appInfo);
        }

        public boolean isPackageAvailable(String packageName) {
            return delegate.isPackageAvailable(this.packageName);
        }
    }

    class ContextDelegate extends ContextWrapper {

        public ContextDelegate(final Context base) {
            super(base);
        }

        @Override
        public String getPackageName() {
            return "com.beastbikes.android";
        }

        @Override
        public PackageManager getPackageManager() {
            final PackageManager pm = super.getPackageManager();
            return new PackageManagerWrapper(pm, super.getPackageName());
        }

        @Override
        public Context getApplicationContext() {
            return this;
        }
    }

    @Test
    public void getLatest() {
        final Context context = new ContextDelegate(Robolectric.setupActivity(Activity.class));
        final CountDownLatch signal = new CountDownLatch(1);
        FirKit.getInstance().getApplicationManager(context).latest(new Callback<ApplicationManager.Version>() {
            @Override
            public void onFailure(final Exception e) {
                e.printStackTrace();
                signal.countDown();
            }

            @Override
            public void onSuccess(final ApplicationManager.Version version) {
                System.out.println(version);
                signal.countDown();
            }
        });

        try {
            signal.await();
        } catch (final InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void listApps() {
        final Context context = new ContextDelegate(Robolectric.setupActivity(Activity.class));
        final CountDownLatch signal = new CountDownLatch(1);
        FirKit.getInstance().getApplicationManager(context).list(new Callback<List<ApplicationManager.Application>>() {
            @Override
            public void onFailure(final Exception e) {
                e.printStackTrace();
                signal.countDown();
            }

            @Override
            public void onSuccess(final List<ApplicationManager.Application> apps) {
                System.out.println(apps);
                signal.countDown();
            }
        });

        try {
            signal.await();
        } catch (final InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

}
