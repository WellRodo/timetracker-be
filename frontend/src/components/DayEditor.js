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
        project_id: "",
        comment: ""
      },
      day: "08.11.2023",  // default значение 
      //day: props.day,
      projectsList: []
    };
  }
 
  componentDidMount() {
    this.refreshList();
  }
  
  refreshList = () => {
    // Получение списка проектов для данного дня
    axios
      .get(`http://127.0.0.1:8000/days/${this.state.day.date}/`)
      .then(res => this.setState({ projectsList: res.data }))
      .catch(err => console.log(err));
  };
 
  // Main variable to render items on the screen
  renderItems = () => {
    return this.state.projectsList.map((project) => (
      <li
        key={project.id}
        className="list-group-item d-flex justify-content-between align-items-center"
      >
        <span>{ project.time } </span> 
        <span>{ project.project_id }</span> 
        <span>{ project.comment }</span>
        
        <span>
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
        </span>
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
        .put(`http://127.0.0.1:8000/days/${item.id}/`, item)
        .then((res) => this.refreshList());
      return;
    }
    // if new post to submit
    axios
      .post(`http://127.0.0.1:8000/days/${item.id}/`, item)
      .then((res) => this.refreshList());
  };
 
  // Delete item
  handleDelete = (item) => {
    alert("delete" + JSON.stringify(item));
    axios
      .delete(`http://127.0.0.1:8000/days/${item.id}/`)
      .then((res) => this.refreshList());
  };
  
  // Create item
  createItem = () => {
    const item = { time: "", project_id: "", comment: "" };
    this.setState({ activeItem: item, modal: !this.state.modal });
  };
 
  //Edit item
  editItem = (item) => {
    this.setState({ activeItem: item, modal: !this.state.modal });
  };
 
  render() {
    return (
      <div>
        <h2 className="text-success text-uppercase text-center my-4">
          Редактирование рабочего дня
        </h2>
        <div className="row ">
          <div className="col-md-6 col-sm-10 mx-auto p-0">
            <div className="card p-3">
              <div className="">
                <button onClick={this.createItem} className="btn btn-info">
                  Добавить проект
                </button>
              </div>
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