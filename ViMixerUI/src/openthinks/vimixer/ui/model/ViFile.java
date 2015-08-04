package openthinks.vimixer.ui.model;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViFile {

	private static AtomicInteger id_generator = new AtomicInteger(0);
	private final IntegerProperty id;
	private final StringProperty filePath;
	private final ObjectProperty<ViFileStatus> status;
	private final ObjectProperty<ViFileInfo> info;

	public ViFile(String filePath) {
		this.id = new SimpleIntegerProperty(id_generator.addAndGet(1));
		this.filePath = new SimpleStringProperty(filePath);
		this.status = new SimpleObjectProperty<ViFileStatus>(ViFileStatus.NOT_START);
		ViFileInfo fileInfo = new ViFileInfo();
		fileInfo.setFileLength(new File(filePath).length());
		this.info = new SimpleObjectProperty<ViFileInfo>(fileInfo);
	}

	public ViFile(File file) {
		this(file.getAbsolutePath());
	}

	public final IntegerProperty idProperty() {
		return this.id;
	}

	public final int getId() {
		return this.idProperty().get();
	}

	public final void setId(final int id) {
		this.idProperty().set(id);
	}

	public final StringProperty filePathProperty() {
		return this.filePath;
	}

	public final File getFile() {
		return new File(getFilePath());
	}

	public final java.lang.String getFilePath() {
		return this.filePathProperty().get();
	}

	public final void setFilePath(final java.lang.String filePath) {
		this.filePathProperty().set(filePath);
	}

	public final ObjectProperty<ViFileStatus> statusProperty() {
		return this.status;
	}

	public final openthinks.vimixer.ui.model.ViFileStatus getStatus() {
		return this.statusProperty().get();
	}

	public final void setStatus(final openthinks.vimixer.ui.model.ViFileStatus status) {
		this.statusProperty().set(status);
	}

	public final void reset() {
		this.status.set(ViFileStatus.NOT_START);
		this.info.get().setBlockLength(0L).setCurrentProcessedBlock(null).setStartTime(null).setEndTime(null)
				.setProcessedBlocks(0L);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filePath == null) ? 0 : filePath.get().hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViFile other = (ViFile) obj;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
			//compare the value in the property
		} else if (!filePath.get().equals(other.filePath.get()))
			return false;
		return true;
	}

	public final ObjectProperty<ViFileInfo> infoProperty() {
		return this.info;
	}

	public final openthinks.vimixer.ui.model.ViFileInfo getInfo() {
		return this.infoProperty().get();
	}

	public final void setInfo(final openthinks.vimixer.ui.model.ViFileInfo info) {
		this.infoProperty().set(info);
	}

}
