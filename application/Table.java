package application;

import java.util.Comparator;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class Table extends DisplayMode {

  private Slider timeSlider;
  private TableView<DataPoint> tableView;
  private VBox settingsPane;
  private DataManager dataManager;
  private FilteredList<DataPoint> filteredList;
  private String[] timeLabels;

  public Table(DataManager dataManager) {
    super();
    title = "table";
    tableView = new TableView<>();
    this.dataManager = dataManager;
    settingsPane = new VBox();
    setupSettings();
    setupTableView();
  }

  @Override
  public Node getDisplayPane() {
    return tableView;
  }

  @Override
  public Node getSettingsPane() {
    return settingsPane;
  }

  @SuppressWarnings("unchecked")
  private void setupTableView() {
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<DataPoint, String> location = new TableColumn<>("Location");
    TableColumn<DataPoint, String> city = new TableColumn<>("City");
    TableColumn<DataPoint, String> state = new TableColumn<>("Province/State");
    TableColumn<DataPoint, String> country = new TableColumn<>("Country/Region");
    TableColumn<DataPoint, String> lat = new TableColumn<>("Lat");
    TableColumn<DataPoint, String> lon = new TableColumn<>("Long");
    TableColumn<DataPoint, String> stats = new TableColumn<>("Stats");
    TableColumn<DataPoint, String> confirmed = new TableColumn<>("Confirmed");
    TableColumn<DataPoint, String> deaths = new TableColumn<>("Deaths");
    TableColumn<DataPoint, String> recovered = new TableColumn<>("Recovered");

    location.setId("column_header_location");
    city.setId("column_city");
    state.setId("column_state");
    country.setId("column_country");
    lat.setId("column_lat");
    lon.setId("column_lon");
    stats.setId("column_header_stats");
    confirmed.setId("column_confirmed");
    deaths.setId("column_deaths");
    recovered.setId("column_recovered");

    city.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("city"));
    city.setComparator(getComparator(city));
    state.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("state"));
    state.setComparator(getComparator(state));
    country.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("country"));
    country.setComparator(getComparator(country));
    lat.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("lat"));
    lon.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("lon"));
    confirmed.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("confirmed"));
    deaths.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("deaths"));
    recovered.setCellValueFactory(new PropertyValueFactory<DataPoint, String>("recovered"));

    location.getColumns().addAll(city, state, country, lat, lon);
    stats.getColumns().addAll(confirmed, deaths, recovered);
    tableView.getColumns().setAll(location, stats);
    tableView.setItems(getInitialTableData());

    tableView.setPlaceholder(new Label("No rows to display"));
  }

  private void setupSettings() {

    // Setup Nodes

    Label sliderLabel = new Label("Choose Time:");
    timeSlider = new Slider(0, 94, 94);
    timeLabels = dataManager.getTimeLabels();
    Label timeLabel = new Label("" + timeLabels[(int) timeSlider.getValue()]);

    TextField cityFilter = new TextField("Filter City");
    TextField stateFilter = new TextField("Filter State");
    TextField countryFilter = new TextField("Filter Country");

    Button setFilter = new Button("Set Filter");
    setFilter.setId("set-filter-btn");

    Button resetFilter = new Button("Reset Filter");
    resetFilter.setId("reset-filter-btn");

    // Add Listeners and Event Handlers

    timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        timeLabel.setText("" + timeLabels[(int) timeSlider.getValue()]);
        DataPoint.time = (int) timeSlider.getValue();
        tableView.refresh();
      }
    });

    cityFilter.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
          Boolean newValue) {
        if (newValue) {
          cityFilter.clear();
        }
        if (oldValue) {
          if (cityFilter.getText().isBlank()) {
            cityFilter.setText("Filter City");
          }
        }
      }

    });

    stateFilter.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
          Boolean newValue) {
        if (newValue) {
          stateFilter.clear();
        }
        if (oldValue) {
          if (stateFilter.getText().isBlank()) {
            stateFilter.setText("Filter State");

          }
        }
      }

    });

    countryFilter.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
          Boolean newValue) {
        if (newValue) {
          countryFilter.clear();
        }
        if (oldValue) {
          if (countryFilter.getText().isBlank()) {
            countryFilter.setText("Filter Country");
          }
        }
      }

    });

    setFilter.setOnAction(e -> {
      filteredList.setPredicate(t -> {
        boolean checkCountry = t.getCountry().equals(countryFilter.getText())
            || countryFilter.getText().equals("Filter Country");
        boolean checkState = t.getState().equals(stateFilter.getText())
            || stateFilter.getText().equals("Filter State");
        boolean checkCity =
            t.getCity().equals(cityFilter.getText()) || cityFilter.getText().equals("Filter City");
        return checkCountry && checkCity && checkState;
      });
    });

    resetFilter.setOnAction(e -> { // button should hide time sliders and
      cityFilter.setText("Filter City");
      stateFilter.setText("Filter State");
      countryFilter.setText("Filter Country");
      filteredList.setPredicate(t -> {
        return true;
      });
    });
    settingsPane.getChildren().addAll(sliderLabel, timeSlider, timeLabel, cityFilter, stateFilter,
        countryFilter, setFilter, resetFilter);
  }

  private SortedList<DataPoint> getInitialTableData() {

    List<DataPoint> list = dataManager.gt.getAll();
    ObservableList<DataPoint> data = FXCollections.observableList(list);
    filteredList = new FilteredList<>(data);

    // to filter
    filteredList.setPredicate(t -> {
      return true;
    });

    SortedList<DataPoint> sortableData = new SortedList<>(this.filteredList);
    sortableData.comparatorProperty().bind(tableView.comparatorProperty());

    return sortableData;
  }

  public List<DataPoint> getFilteredList() {
    return filteredList;
  }

  private Comparator<String> getComparator(TableColumn<DataPoint, String> tc) {
    return (o1, o2) -> {
      final boolean isDesc = tc.getSortType() == SortType.DESCENDING;
      if (o1.equals("") && o2.equals(""))
        return 0;
      else if (o1.equals("") && !o2.equals(""))
        return isDesc ? -1 : 1;
      else if (!o1.equals("") && o2.equals(""))
        return isDesc ? 1 : -1;
      else
        return o1.compareTo(o2);
    };
  }

}
