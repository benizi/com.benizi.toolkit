package com.benizi.toolkit;

import java.awt.Desktop;
import java.net.URI;
import sun.awt.UNIXToolkit;
import sun.awt.X11.XDesktopPeer;
import sun.awt.X11.XToolkit;

public class Y extends UNIXToolkit {
  private final XToolkit x;
  public Y() { x = new XToolkit(); }

  public void browse(URI uri) { System.out.println(uri.toString()); }

  public java.awt.peer.DesktopPeer
  createDesktopPeer(final java.awt.Desktop arg0)
      throws java.awt.HeadlessException {
    final java.awt.peer.DesktopPeer p = x.createDesktopPeer(arg0);
    return new java.awt.peer.DesktopPeer() {
      public void browse(final java.net.URI arg0) throws java.io.IOException {
        //System.out.println(arg0);
        String[] cmd = {"open", arg0.toString()};
        Runtime.getRuntime().exec(cmd);
      }
      public void print(final java.io.File arg0) throws java.io.IOException {p.print(arg0);}
      public void open(final java.io.File arg0) throws java.io.IOException {p.open(arg0);}
      public void mail(final java.net.URI arg0) throws java.io.IOException {p.mail(arg0);}
      public boolean isSupported(final java.awt.Desktop.Action arg0) {
        if (arg0 == Desktop.Action.BROWSE) {
          return true;
        }
        return p.isSupported(arg0);
      }
      public void edit(final java.io.File arg0) throws java.io.IOException {p.edit(arg0);}
    };
  }

  public static void main(String[] args) throws java.net.URISyntaxException, java.io.IOException {
    Desktop.getDesktop().browse(new URI("benizi.com"));
  }

public java.awt.peer.TextAreaPeer createTextArea(final java.awt.TextArea arg0) throws java.awt.HeadlessException {return x.createTextArea(arg0);}
public java.awt.peer.SystemTrayPeer createSystemTray(final java.awt.SystemTray arg0) {return x.createSystemTray(arg0);}
public java.awt.peer.ButtonPeer createButton(final java.awt.Button arg0) throws java.awt.HeadlessException {return x.createButton(arg0);}
public java.awt.peer.TrayIconPeer createTrayIcon(final java.awt.TrayIcon arg0) throws java.awt.HeadlessException, java.awt.AWTException {return x.createTrayIcon(arg0);}
public java.awt.dnd.peer.DragSourceContextPeer createDragSourceContextPeer(final java.awt.dnd.DragGestureEvent arg0) throws java.awt.dnd.InvalidDnDOperationException {return x.createDragSourceContextPeer(arg0);}
public java.awt.peer.FontPeer getFontPeer(final java.lang.String arg0, final int arg1) {return x.getFontPeer(arg0, arg1);}
public java.awt.im.spi.InputMethodDescriptor getInputMethodAdapterDescriptor() throws java.awt.AWTException {return x.getInputMethodAdapterDescriptor();}
public java.awt.peer.FramePeer createFrame(final java.awt.Frame arg0) throws java.awt.HeadlessException {return x.createFrame(arg0);}
public boolean isDesktopSupported() {return x.isDesktopSupported();}
public void grab(final java.awt.Window arg0) {x.grab(arg0);}
public int getScreenResolution() throws java.awt.HeadlessException {return x.getScreenResolution();}
public boolean isTraySupported() {return x.isTraySupported();}
public java.awt.peer.RobotPeer createRobot(final java.awt.Robot arg0, final java.awt.GraphicsDevice arg1) throws java.awt.AWTException {return x.createRobot(arg0, arg1);}
public java.util.Map mapInputMethodHighlight(final java.awt.im.InputMethodHighlight arg0) throws java.awt.HeadlessException {return x.mapInputMethodHighlight(arg0);}
public java.awt.peer.MenuItemPeer createMenuItem(final java.awt.MenuItem arg0) throws java.awt.HeadlessException {return x.createMenuItem(arg0);}
public void beep() {x.beep();}
public java.awt.peer.TextFieldPeer createTextField(final java.awt.TextField arg0) throws java.awt.HeadlessException {return x.createTextField(arg0);}
public java.awt.peer.FileDialogPeer createFileDialog(final java.awt.FileDialog arg0) throws java.awt.HeadlessException {return x.createFileDialog(arg0);}
public java.awt.peer.CheckboxMenuItemPeer createCheckboxMenuItem(final java.awt.CheckboxMenuItem arg0) throws java.awt.HeadlessException {return x.createCheckboxMenuItem(arg0);}
public void ungrab(final java.awt.Window arg0) {x.ungrab(arg0);}
protected boolean syncNativeQueue(final long arg0) {return true;}
public java.awt.peer.CheckboxPeer createCheckbox(final java.awt.Checkbox arg0) throws java.awt.HeadlessException {return x.createCheckbox(arg0);}
public java.awt.peer.DialogPeer createDialog(final java.awt.Dialog arg0) throws java.awt.HeadlessException {return x.createDialog(arg0);}
public java.awt.peer.MenuBarPeer createMenuBar(final java.awt.MenuBar arg0) throws java.awt.HeadlessException {return x.createMenuBar(arg0);}
public java.awt.peer.MenuPeer createMenu(final java.awt.Menu arg0) throws java.awt.HeadlessException {return x.createMenu(arg0);}
public sun.awt.datatransfer.DataTransferer getDataTransferer() {return x.getDataTransferer();}
protected int getScreenHeight() {return 100;}
public java.awt.image.ColorModel getColorModel() throws java.awt.HeadlessException {return x.getColorModel();}
public java.awt.peer.PopupMenuPeer createPopupMenu(final java.awt.PopupMenu arg0) throws java.awt.HeadlessException {return x.createPopupMenu(arg0);}
public java.awt.peer.KeyboardFocusManagerPeer getKeyboardFocusManagerPeer() throws java.awt.HeadlessException {return x.getKeyboardFocusManagerPeer();}
public java.awt.peer.ChoicePeer createChoice(final java.awt.Choice arg0) throws java.awt.HeadlessException {return x.createChoice(arg0);}
public java.awt.peer.LabelPeer createLabel(final java.awt.Label arg0) throws java.awt.HeadlessException {return x.createLabel(arg0);}
public java.awt.PrintJob getPrintJob(final java.awt.Frame arg0, final java.lang.String arg1, final java.util.Properties arg2) {return x.getPrintJob(arg0, arg1, arg2);}
public java.awt.peer.ListPeer createList(final java.awt.List arg0) throws java.awt.HeadlessException {return x.createList(arg0);}
protected int getScreenWidth() {return 100;}
public java.awt.datatransfer.Clipboard getSystemClipboard() throws java.awt.HeadlessException {return x.getSystemClipboard();}
public java.awt.peer.WindowPeer createWindow(final java.awt.Window arg0) throws java.awt.HeadlessException {return x.createWindow(arg0);}
public java.awt.peer.ScrollPanePeer createScrollPane(final java.awt.ScrollPane arg0) throws java.awt.HeadlessException {return x.createScrollPane(arg0);}
public java.awt.peer.ScrollbarPeer createScrollbar(final java.awt.Scrollbar arg0) throws java.awt.HeadlessException {return x.createScrollbar(arg0);}
public java.awt.peer.FramePeer createLightweightFrame(final sun.awt.LightweightFrame arg0) throws java.awt.HeadlessException {return x.createLightweightFrame(arg0);}
}
