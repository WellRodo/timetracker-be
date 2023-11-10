import React, { Component } from "react";
import axios from 'axios';  
import DayModal from './DayModal';

// Редактор рабочего дня.
// Пользователь редактирует список с проектами, временем и комментариями.
class DayEditor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeItem: {
        time: "",
        projectId: "",
        comment: ""
      },
      isFinished: false,
      day: "08.11.2023",  // default значение 
      //day: props.day,
      projectsList: [
        {
          time: "3:00",
          projectId: "b468530e-7fa3-11ee-b962-0242ac120002",
          comment: "Реализация frontend части timetracker",
          isFinished: false
        },
        {
          time: "1:20",
          projectId: "d268530e-8sq2-14ee-b762-3461dc124444",
          comment: "Реализация backend части timetracker",
          isFinished: false
        },
        {
          time: "1:00",
          projectId: "k666432r-1rr2-31ye-y942-2437kp076046",
          comment: "Рефакторинг библиотеки Tracking",
          isFinished: false
        }
      ]
    };
  }
 
  componentDidMount() {
    this.refreshList();
  }
  
  refreshList = () => {
    // Получение списка таймшитов для данного дня
    axios
      .get(`http://127.0.0.1:8000/day/${this.state.day.date}/`)
      .then(res => this.setState({ projectsList: res.data }))
      .catch(err => console.log(err));
    if (this.state.projectsList.length > 0) {
      this.setState({ isFinished: this.state.projectsList[0].isFinished });
    }
  };
 
  // Main variable to render items on the screen
  renderItems = () => {
    return this.state.projectsList.map((project) => (
      <li
        key={project.id}
        className="list-group-item d-flex justify-content-between align-items-center"
      >
        
        <span>{ project.time }</span> 
        <span>{ project.projectId }</span>
        <span>{ project.comment }</span>
        
        { this.state.isFinished ? "" : (
          <div>
            <button
              onClick={() => this.editItem(project)}
              className="btn btn-secondary mr-2"
            >
              Edit
            </button>
            <button
              onClick={() => this.handleDelete(project)}
              className="btn btn-danger"
            >
              Delete
            </button>
          </div>
        )}
      </li>
    ));
  };
 
  toggle = () => {
    this.setState({ modal: !this.state.modal });
  };
 
 
  // Submit an item
  handleSubmit = (item) => {
    this.toggle();
    alert("save" + JSON.stringify(item));
    if (item.id) {
      // if old post to edit and submit
      axios
        .put(`http://127.0.0.1:8000/day/${item.id}/`, item)
        .then((res) => this.refreshList());
      return;
    }
    // if new post to submit
    axios
      .post(`http://127.0.0.1:8000/day/${item.id}/`, item)
      .then((res) => this.refreshList());
  };
 
  // Delete item
  handleDelete = (item) => {
    alert("delete" + JSON.stringify(item));
    axios
      .delete(`http://127.0.0.1:8000/day/${item.id}/`)
      .then((res) => this.refreshList());
  };
  
  // Create item
  createItem = () => {
    const item = { time: "", projectId: "", comment: "" };
    this.setState({ activeItem: item, modal: !this.state.modal });
  };
 
  //Edit item
  editItem = (item) => {
    this.setState({ activeItem: item, modal: !this.state.modal });
  };

  //Send (make day FINISHED)
  makeItemsFinished = () => {
    for (let i = 0; i < this.state.projectsList.length; i++) {
      this.state.projectsList[i].isFinished = true;
      axios.put(`http://127.0.0.1:8000/day/${this.state.projectsList[i].id}/`, this.state.projectsList[i]);
    }
    this.refreshList();
  }
 
  render() {
    return (
      <div>
        <h2 className="text-success text-uppercase text-center my-4">
          Рабочий день {this.state.day}
        </h2>
        <div className="row ">
          { this.state.isFinished ? "" : (
            <div className="">
              <button onClick={this.createItem} className="btn btn-info">
                Добавить проект
              </button>
              <button onClick={this.makeItemsFinished} className="btn btn-success">
                Отправить
              </button>
            </div>
          )}
          <div className="col-md-6 col-sm-10 mx-auto p-0">
            <div className="card p-3">
              <ul className="list-group list-group-flush">
                {this.renderItems()}
              </ul>
            </div>
          </div>
        </div>
        {this.state.modal ? (
          <DayModal
            activeItem={this.state.activeItem}
            toggle={this.toggle}
            onSave={this.handleSubmit}
          />
        ) : null}
      </div>
    );
  }
}
export default DayEditor;